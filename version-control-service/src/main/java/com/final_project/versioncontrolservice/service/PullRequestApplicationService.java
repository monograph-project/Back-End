package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.dto.*;
import com.final_project.versioncontrolservice.event.RepositoryOperationEvent;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.kafka.KafkaProducer;
import com.final_project.versioncontrolservice.model.*;
import com.final_project.versioncontrolservice.repo.PullRequestConflictRepository;
import com.final_project.versioncontrolservice.repo.PullRequestRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PullRequestApplicationService {
    private final PullRequestMergeService pullRequestMergeService;
    private final PullRequestConflictRepository pullRequestConflictRepository;

    private final PullRequestRepository pullRequestRepository;
    private final CommitGraphService commitGraphService;
    private final RepositoryService repositoryService;
    private final AuthService authService;
    private final KafkaProducer kafkaProducer;
    public PullRequestResponse create(
            String owner,
            String repo,
            CreatePullRequest request
    ) {
        ContributorUser authorContributor =  authService.getContributorUser(request.getAuthor());
        if (authorContributor == null) {
            throw new NotFoundException("Contributor Not Found");
        }
        UserDTO repoOwner = authService.getUserByUsername(owner);
        if (repoOwner == null) {
            throw new NotFoundException("User Not Found");
        }

        RepositoryDocument currentDocument = repositoryService.loadMeta(owner, repo);
        if (currentDocument == null) {
            throw new NotFoundException("The Current Repository does not exist");
        }

        String headSource = repositoryService.listBranchHash(currentDocument, request.getSourceBranch());
        if (headSource.isEmpty()) {
            throw new BadRequestException("source branch \"" + request.getSourceBranch() + "\" does not exist");
        }

        String headTarget = repositoryService.listBranchHash(currentDocument, request.getTargetBranch());
        if (headTarget.isEmpty()) {
            throw new BadRequestException("target branch \"" + request.getTargetBranch()  + "\" does not exist");
        }

       PullRequest pullRequest = PullRequest
               .builder()
               .repoName(repo)
               .sourceBranch(request.getSourceBranch())
               .sourceHash(headSource)
               .targetBranch(request.getTargetBranch())
               .targetHash(headTarget)
               .status(PullRequestStatus.OPENED)
               .author(
                       PullRequestUser
                               .builder()
                               .email(authorContributor.getEmail())
                               .firstName(authorContributor.getFirstName())
                               .username(authorContributor.getUsername())
                               .id(authorContributor.getId())
                               .profile(authorContributor.getProfile())
                               .build()
               )

               .createdAt(Instant.now())
               .description(request.getDescription())
               .title(request.getTitle())
               .repoOwner(
                       PullRequestUser
                               .builder()
                               .email(repoOwner.getEmail())
                               .firstName(repoOwner.getFirstName())
                               .username(repoOwner.getUsername())
                               .id(repoOwner.getId())
                               .profile(repoOwner.getProfile())
                               .build()
               )
               .build();
        PullRequest saved = pullRequestRepository.save(pullRequest);
        publishPullRequestOpenedEvent(saved, currentDocument);
        return PullRequestResponse.from(saved);
    }

    public List<PullRequestResponse> list(String owner, String repo) {

        RepositoryDocument document = repositoryService.loadMeta(owner, repo);
        if (document == null) {
            throw new  NotFoundException("The Current Repository does not exist");
        }
        List<PullRequest> list =  pullRequestRepository
                .findByRepoOwner_UsernameIgnoreCaseAndRepoNameIgnoreCase(document.getOwner().getUsername(), document.getRepositoryName());

        return list
                .stream()
                .map(PullRequestResponse::from).collect(Collectors.toList());
    }

    public PullRequestResponse find(String id, String owner, String repo) {
        RepositoryDocument document = repositoryService.loadMeta(owner, repo);
        if (document == null) {
            throw new  NotFoundException("The Current Repository does not exist");
        }

        PullRequest result =  pullRequestRepository
                .findByIdAndRepoOwner_UsernameIgnoreCaseAndRepoNameIgnoreCase(id, document.getOwner().getUsername(), document.getRepositoryName())
                .orElseThrow(() -> new NotFoundException("pull request not found"));
        return PullRequestResponse.from(result);
    }



    public MergeResponse merge(String pullId, String owner, String repoName) throws IOException {
        RepositoryDocument document = repositoryService.loadMeta(owner, repoName);
        if (document == null) {
            throw new  NotFoundException("The Current Repository does not exist");
        }
        PullRequest pullRequest = pullRequestRepository.findById(pullId)
                .orElseThrow(() -> new NotFoundException("Pull Request not found"));

        if (!pullRequest.getStatus().equals(PullRequestStatus.OPENED)){
            throw new  BadRequestException("Pull Request Status Not Opened");
        }

        String sourceHash = repositoryService.listBranchHash(document, pullRequest.getSourceBranch());

        if (sourceHash.isEmpty()) {
            throw new BadRequestException("source branch does not exist");
        }

        String targetHash = repositoryService.listBranchHash(document, pullRequest.getTargetBranch());

        if (targetHash.isEmpty()) {
            throw new BadRequestException("target branch does not exist");
        }

        if (sourceHash.equals(targetHash)) {
            markMerged(pullRequest.getId());

            return MergeResponse.builder()
                    .mergedAt(Instant.now())
                    .pullRequestId(pullRequest.getId())
                    .status(PullRequestStatus.MERGED)
                    .targetBranch(pullRequest.getTargetBranch())
                    .sourceBranch(pullRequest.getSourceBranch())
                    .newHead(targetHash)
                    .message("Already up to date")
                    .build();
        }

        boolean canFf;

        try {
            canFf = commitGraphService.isAncestorInRepo(
                    document.getOwner().getUsername(),
                    document.getRepositoryName(),
                    targetHash,
                    sourceHash
            );
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        if (canFf) {
            RepositoryDocument mergedRepository = repositoryService.loadMeta(
                    document.getOwner().getUsername(),
                    document.getRepositoryName()
            );

            repositoryService.updateBranchRef(
                    mergedRepository,
                    pullRequest.getTargetBranch(),
                    sourceHash
            );

            pullRequest.setStatus(PullRequestStatus.MERGED);
            pullRequest.setMergedAt(Instant.now());
            pullRequest.setTargetHash(sourceHash);
            PullRequest saved = pullRequestRepository.save(pullRequest);

            publishPullRequestMergedEvent(saved, mergedRepository);

            return MergeResponse.builder()
                    .mergedAt(saved.getMergedAt())
                    .pullRequestId(saved.getId())
                    .status(PullRequestStatus.MERGED)
                    .targetBranch(saved.getTargetBranch())
                    .sourceBranch(saved.getSourceBranch())
                    .newHead(sourceHash)
                    .message("Fast-forward merge")
                    .build();
        }


        //  NON FAST-FORWARD → REAL MERGE
        String baseHash = commitGraphService.findCommonAncestor(
                document.getOwner().getUsername(),
                document.getRepositoryName(),
                targetHash,
                sourceHash
        );

        PullRequestMergeService.MergeAnalysis analysis =
                pullRequestMergeService.analyze(
                        document.getOwner().getUsername(),
                        document.getRepositoryName(),
                        baseHash,
                        targetHash,
                        sourceHash
                );

        //  IF CONFLICT → STORE IN DB
        if (analysis.isHasConflicts()) {

            pullRequestConflictRepository.deleteByPullRequestId(pullRequest.getId());

            var conflict = com.final_project.versioncontrolservice.model.PullRequestConflict.builder()
                    .pullRequestId(pullRequest.getId())
                    .owner(document.getOwner().getUsername())
                    .repoName(document.getRepositoryName())
                    .sourceBranch(pullRequest.getSourceBranch())
                    .targetBranch(pullRequest.getTargetBranch())
                    .baseHash(baseHash)
                    .sourceHash(sourceHash)
                    .targetHash(targetHash)
                    .files(
                            analysis.getConflicts().stream()
                                    .map(c -> PullRequestConflict.ConflictFile.builder()
                                            .path(c.getPath())
                                            .binary(c.isBinary())
                                            .baseHash(c.getBaseHash())
                                            .sourceHash(c.getSourceHash())
                                            .targetHash(c.getTargetHash())
                                            .segments(
                                                    c.getSegments().stream()
                                                            .map(segment -> PullRequestConflict.FileSegment.builder()
                                                                    .id(segment.getId())
                                                                    .orderIndex(segment.getOrderIndex())
                                                                    .type(segment.getType())
                                                                    .content(segment.getContent())
                                                                    .baseStartLine(segment.getBaseStartLine())
                                                                    .baseEndLine(segment.getBaseEndLine())
                                                                    .sourceStartLine(segment.getSourceStartLine())
                                                                    .sourceEndLine(segment.getSourceEndLine())
                                                                    .targetStartLine(segment.getTargetStartLine())
                                                                    .targetEndLine(segment.getTargetEndLine())
                                                                    .baseChunk(segment.getBaseChunk())
                                                                    .sourceChunk(segment.getSourceChunk())
                                                                    .targetChunk(segment.getTargetChunk())
                                                                    .resolved(false)
                                                                    .build())
                                                            .toList()
                                            )
                                            .resolved(false)
                                            .build()
                                    )
                                    .toList()
                    )

                    .resolved(false)
                    .createdAt(Instant.now())
                    .build();

            pullRequestConflictRepository.save(conflict);

            pullRequest.setStatus(PullRequestStatus.CONFLICTING);
            PullRequest saved =  pullRequestRepository.save(pullRequest);
            throw new BadRequestException("Merge conflict detected. Resolve conflicts first.");
        }

        //  NO CONFLICT → CREATE MERGE COMMIT
        String mergeCommitHash = pullRequestMergeService.createMergeCommit(
                document.getOwner().getUsername(),
                document.getRepositoryName(),
                analysis.getMergedTreeHash(),
                targetHash,
                sourceHash,
                "Merge branch '" + pullRequest.getSourceBranch() +
                        "' into '" + pullRequest.getTargetBranch() + "'",
                pullRequest.getAuthor().getUsername()
        );

        // UPDATE BRANCH
        RepositoryDocument mergedRepository = repositoryService.loadMeta(
                document.getOwner().getUsername(),
                document.getRepositoryName()
        );

        repositoryService.updateBranchRef(
                mergedRepository,
                pullRequest.getTargetBranch(),
                mergeCommitHash
        );

        pullRequest.setStatus(PullRequestStatus.MERGED);
        pullRequest.setMergedAt(Instant.now());
        pullRequest.setTargetHash(mergeCommitHash);
        PullRequest saved = pullRequestRepository.save(pullRequest);
        publishPullRequestMergedEvent(saved, mergedRepository);



        return MergeResponse.builder()
                .mergedAt(Instant.now())
                .pullRequestId(pullRequest.getId())
                .status(PullRequestStatus.MERGED)
                .targetBranch(pullRequest.getTargetBranch())
                .sourceBranch(pullRequest.getSourceBranch())
                .newHead(mergeCommitHash)
                .message("Merged successfully")
                .build();
    }

    public MergeConflictResponse getConflicts(String pullId, String owner, String repoName) {
        RepositoryDocument document = repositoryService.loadMeta(owner, repoName);

        PullRequest pullRequest = pullRequestRepository
                .findByIdAndRepoOwner_UsernameIgnoreCaseAndRepoNameIgnoreCase(
                        pullId,
                        document.getOwner().getUsername(),
                        document.getRepositoryName()
                )
                .orElseThrow(() -> new NotFoundException("Pull request not found"));

        PullRequestConflict conflict = pullRequestConflictRepository
                .findByPullRequestIdAndResolvedFalse(pullRequest.getId())
                .orElseThrow(() -> new NotFoundException("No active conflicts found for this pull request"));

        return MergeConflictResponse.builder()
                .pullRequestId(pullRequest.getId())
                .status(pullRequest.getStatus().name())
                .conflicts(
                        conflict.getFiles().stream()
                                .map(file -> MergeConflictResponse.ConflictFileDTO.builder()
                                        .path(file.getPath())
                                        .binary(file.isBinary())
                                        .segments(
                                                file.getSegments().stream()
                                                        .map(segment -> MergeConflictResponse.SegmentDTO.builder()
                                                                .id(segment.getId())
                                                                .orderIndex(segment.getOrderIndex())
                                                                .type(segment.getType().name())
                                                                .content(segment.getContent())
                                                                .sourceStartLine(segment.getSourceStartLine())
                                                                .sourceEndLine(segment.getSourceEndLine())
                                                                .targetStartLine(segment.getTargetStartLine())
                                                                .targetEndLine(segment.getTargetEndLine())
                                                                .baseChunk(file.isBinary() ? null : segment.getBaseChunk())
                                                                .sourceChunk(file.isBinary() ? null : segment.getSourceChunk())
                                                                .targetChunk(file.isBinary() ? null : segment.getTargetChunk())
                                                                .resolved(segment.isResolved())
                                                                .resolvedChunk(file.isBinary() ? null : segment.getResolvedChunk())
                                                                .resolution(segment.getResolution() == null ? null : segment.getResolution().name())
                                                                .build())
                                                        .toList()
                                        )
                                        .build())
                                .toList()
                )
                .build();
    }

    public MergeResponse resolveConflicts(
            String pullId,
            String owner,
            String repoName,
            ResolveConflictRequest request
    ) throws IOException {
        if (request == null || request.getFiles() == null || request.getFiles().isEmpty()) {
            throw new BadRequestException("Conflict resolutions are required");
        }

        RepositoryDocument document = repositoryService.loadMeta(owner, repoName);

        PullRequest pullRequest = pullRequestRepository
                .findByIdAndRepoOwner_UsernameIgnoreCaseAndRepoNameIgnoreCase(
                        pullId,
                        document.getOwner().getUsername(),
                        document.getRepositoryName()
                )
                .orElseThrow(() -> new NotFoundException("Pull request not found"));

        PullRequestConflict conflict = pullRequestConflictRepository
                .findByPullRequestIdAndResolvedFalse(pullRequest.getId())
                .orElseThrow(() -> new NotFoundException("No active conflicts found for this pull request"));

        Map<String, ResolveConflictRequest.FileResolution> fileResolutions =
                request.getFiles()
                        .stream()
                        .collect(Collectors.toMap(
                                ResolveConflictRequest.FileResolution::getPath,
                                Function.identity()
                        ));

        Map<String, String> resolvedContentByPath = new java.util.HashMap<>();

        for (PullRequestConflict.ConflictFile file : conflict.getFiles()) {
            ResolveConflictRequest.FileResolution fileResolution = fileResolutions.get(file.getPath());

            if (fileResolution.getBlocks() == null || fileResolution.getBlocks().isEmpty()) {
                throw new BadRequestException("Missing block resolutions for file: " + file.getPath());
            }


            Map<String, ResolveConflictRequest.BlockResolution> blockResolutions =
                    fileResolution.getBlocks()
                            .stream()
                            .collect(Collectors.toMap(
                                    ResolveConflictRequest.BlockResolution::getBlockId,
                                    Function.identity()
                            ));

            String resolvedContent = buildResolvedFileContent(file, blockResolutions);

            file.setResolved(true);
            resolvedContentByPath.put(file.getPath(), resolvedContent);
            file.setResolved(true);
        }

        String mergedTreeHash = pullRequestMergeService.writeMergedTreeFromResolvedFiles(
                document.getOwner().getUsername(),
                document.getRepositoryName(),
                new java.util.TreeMap<>(),
                resolvedContentByPath
        );

        String mergeCommitHash = pullRequestMergeService.createMergeCommit(
                document.getOwner().getUsername(),
                document.getRepositoryName(),
                mergedTreeHash,
                conflict.getTargetHash(),
                conflict.getSourceHash(),
                "Merge branch '" + pullRequest.getSourceBranch() +
                        "' into '" + pullRequest.getTargetBranch() + "'",
                pullRequest.getAuthor().getUsername()
        );

        repositoryService.updateBranchRef(
                document,
                pullRequest.getTargetBranch(),
                mergeCommitHash
        );

        conflict.setResolved(true);
        conflict.setResolvedAt(Instant.now());
        pullRequestConflictRepository.save(conflict);

        pullRequest.setStatus(PullRequestStatus.MERGED);
        pullRequest.setMergedAt(Instant.now());
        pullRequest.setTargetHash(mergeCommitHash);
        pullRequestRepository.save(pullRequest);


        return MergeResponse.builder()
                .mergedAt(pullRequest.getMergedAt())
                .pullRequestId(pullRequest.getId())
                .status(PullRequestStatus.MERGED)
                .targetBranch(pullRequest.getTargetBranch())
                .sourceBranch(pullRequest.getSourceBranch())
                .newHead(mergeCommitHash)
                .message("Conflicts resolved and pull request merged")
                .build();
    }

    private String buildResolvedFileContent(
            PullRequestConflict.ConflictFile file,
            Map<String, ResolveConflictRequest.BlockResolution> blockResolutions
    ) {
        StringBuilder out = new StringBuilder();

        for (PullRequestConflict.FileSegment segment : file.getSegments()) {
            if (segment.getType() == PullRequestConflict.SegmentType.PLAIN) {
                out.append(segment.getContent() == null ? "" : segment.getContent());
                continue;
            }

            ResolveConflictRequest.BlockResolution blockResolution =
                    blockResolutions.get(segment.getId());

            if (blockResolution == null) {
                throw new BadRequestException(
                        "Missing resolution for conflict block: " + segment.getId()
                );
            }

            String selectedContent = resolveBlockContent(segment, blockResolution);

            segment.setResolution(blockResolution.getResolution());
            segment.setResolvedChunk(selectedContent);
            segment.setResolved(true);

            out.append(selectedContent == null ? "" : selectedContent);
        }

        return out.toString();
    }

    private String resolveBlockContent(
            PullRequestConflict.FileSegment segment,
            ResolveConflictRequest.BlockResolution blockResolution
    ) {
        return switch (blockResolution.getResolution()) {
            case SOURCE -> segment.getSourceChunk() == null ? "" : segment.getSourceChunk();

            case TARGET -> segment.getTargetChunk() == null ? "" : segment.getTargetChunk();

            case BOTH -> {
                String target = segment.getTargetChunk() == null ? "" : segment.getTargetChunk();
                String source = segment.getSourceChunk() == null ? "" : segment.getSourceChunk();
                yield target + System.lineSeparator() + source;
            }

            case CUSTOM -> {
                if (blockResolution.getCustomContent() == null) {
                    throw new BadRequestException(
                            "Custom content is required for block: " + segment.getId()
                    );
                }
                yield blockResolution.getCustomContent();
            }
        };
    }

    private void markMerged(String  id) {
        PullRequest pr = pullRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("pull request not found"));
        pr.setStatus(PullRequestStatus.MERGED);
        pr.setMergedAt(Instant.now());
        pullRequestRepository.save(pr);
    }

    private boolean isBinaryContent(String content) {
        if (content == null || content.isEmpty()) return false;

        return content.contains("\u0000") || !content.chars().allMatch(c -> c >= 32 || c == '\n' || c == '\r' || c == '\t');
    }


    private void publishPullRequestOpenedEvent(
            PullRequest pullRequest,
            RepositoryDocument repository
    ) {
        RepositoryOperationEvent event = RepositoryOperationEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(RepositoryEventType.PULL_REQUEST_OPENED)

                .repositoryId(repository.getId())
                .repositoryName(repository.getRepositoryName())
                .repositoryUrl("/api/v1/repos/" + repository.getOwner().getUsername() + "/" + repository.getRepositoryName() + "/pulls/" + pullRequest.getId())

                .actorUserId(pullRequest.getAuthor().getId())
                .actorName(pullRequest.getAuthor().getUsername())
                .actorEmail(pullRequest.getAuthor().getEmail())

                .ownerUserId(pullRequest.getRepoOwner().getId())
                .ownerName(pullRequest.getRepoOwner().getUsername())
                .ownerEmail(pullRequest.getRepoOwner().getEmail())

                .sourceBranch(pullRequest.getSourceBranch())
                .targetBranch(pullRequest.getTargetBranch())
                .pullRequestId(pullRequest.getId())
                .pullRequestTitle(pullRequest.getTitle())
                .pullRequestUrl("/api/v1/repos/" + repository.getOwner().getUsername()
                        + "/" + repository.getRepositoryName()
                        + "/pulls/" + pullRequest.getId())
                .recipients(
                        repository
                                .getCollaborators()
                                .stream()
                                .map((repo -> RepositoryMemberRecipient.builder()
                                            .userId(repo.getId())
                                            .name(repo.getUsername())
                                            .email(repo.getEmail())
                                            .role(repo.getRole()).build()
                                )).toList()
                )
                .occurredAt(LocalDateTime.now())
                .metadata(Map.of(
                        "actionUrl", "/api/v1/repos" + repository.getOwner().getUsername() + "/" + repository.getRepositoryName()+"/"+pullRequest.getId(),
                        "displayType", "PULL_REQUEST_OPENED",
                        "description", pullRequest.getDescription() == null ? "" : pullRequest.getDescription(),
                        "message", pullRequest.getAuthor().getUsername()
                                + " opened a pull request: "
                                + pullRequest.getTitle()
                ))
                .build();

        kafkaProducer.produce(event);
    }

    private void publishPullRequestMergedEvent(
            PullRequest pullRequest,
            RepositoryDocument repository
    ) {
        RepositoryOperationEvent event = RepositoryOperationEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(RepositoryEventType.PULL_REQUEST_MERGED)

                .repositoryId(repository.getId())
                .repositoryName(repository.getRepositoryName())
                .repositoryUrl("/api/v1/repos/" + repository.getOwner().getUsername() + "/" + repository.getRepositoryName() + "/contents?ref=main")
                .actorUserId(pullRequest.getAuthor().getId())
                .actorName(pullRequest.getAuthor().getUsername())
                .actorEmail(pullRequest.getAuthor().getEmail())

                .ownerUserId(pullRequest.getRepoOwner().getId())
                .ownerName(pullRequest.getRepoOwner().getUsername())
                .ownerEmail(pullRequest.getRepoOwner().getEmail())

                .sourceBranch(pullRequest.getSourceBranch())
                .targetBranch(pullRequest.getTargetBranch())

                .pullRequestId(pullRequest.getId())
                .pullRequestTitle(pullRequest.getTitle())
                .pullRequestUrl("/api/v1/repos/"+repository.getOwner().getUsername()+"/"+repository.getRepositoryName()+"/pulls/"+pullRequest.getId())
                .recipients(
                        repository
                                .getCollaborators()
                                .stream()
                                .map((repo -> RepositoryMemberRecipient.builder()
                                            .userId(repo.getId())
                                            .name(repo.getUsername())
                                            .email(repo.getEmail())
                                            .role(repo.getRole()).build()
                                )).toList()
                )
                .occurredAt(LocalDateTime.now())
                .metadata(Map.of(
                        "actionUrl", "/api/v1/repos/" + repository.getOwner().getUsername() + "/" + repository.getRepositoryName() + "/pulls/" + pullRequest.getId(),
                        "displayType", "PULL_REQUEST_MERGED",
                        "message", pullRequest.getAuthor().getUsername()
                                + " merged pull request: "
                                + pullRequest.getTitle()
                ))
                .build();

        kafkaProducer.produce(event);
    }
}
