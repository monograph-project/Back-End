package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.dto.*;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.PullRequestConflict;
import com.final_project.versioncontrolservice.model.PullRequestStatus;
import com.final_project.versioncontrolservice.repo.PullRequestConflictRepository;
import com.final_project.versioncontrolservice.repo.PullRequestRepository;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
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



    public MergeResponse merge(String pullId, String owner, String repoName) {
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

            markMerged(pullRequest.getId());

            return MergeResponse.builder()
                    .mergedAt(Instant.now())
                    .pullRequestId(pullRequest.getId())
                    .status(PullRequestStatus.MERGED)
                    .targetBranch(pullRequest.getTargetBranch())
                    .sourceBranch(pullRequest.getSourceBranch())
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
                                    .map(c -> com.final_project.versioncontrolservice.model.PullRequestConflict.ConflictFile.builder()
                                            .path(c.getPath())
                                            .baseHash(c.getBaseHash())
                                            .sourceHash(c.getSourceHash())
                                            .targetHash(c.getTargetHash())
                                            .baseContent(c.getBaseContent())
                                            .sourceContent(c.getSourceContent())
                                            .targetContent(c.getTargetContent())
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
            pullRequestRepository.save(pullRequest);

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

        markMerged(pullRequest.getId());

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
                        conflict.getFiles()
                                .stream()
                                .map(file -> {
                                    boolean isBinary =
                                            isBinaryContent(file.getBaseContent()) ||
                                                    isBinaryContent(file.getSourceContent()) ||
                                                    isBinaryContent(file.getTargetContent());

                                    return MergeConflictResponse.ConflictFileDTO.builder()
                                            .path(file.getPath())
                                            .binary(isBinary)
                                            .baseContent(isBinary ? null : file.getBaseContent())
                                            .sourceContent(isBinary ? null : file.getSourceContent())
                                            .targetContent(isBinary ? null : file.getTargetContent())
                                            .build();
                                })
                                .toList()
                )
                .build();
    }
    public MergeResponse resolveConflicts(
            String pullId,
            String owner,
            String repoName,
            ResolveConflictRequest request
    ) {
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

        Map<String, ResolveConflictRequest.FileResolution> resolutions =
                request.getFiles()
                        .stream()
                        .collect(Collectors.toMap(
                                ResolveConflictRequest.FileResolution::getPath,
                                Function.identity()
                        ));

        Map<String, String> resolvedContentByPath = new java.util.HashMap<>();

        for (PullRequestConflict.ConflictFile file : conflict.getFiles()) {
            ResolveConflictRequest.FileResolution resolution = resolutions.get(file.getPath());

            if (resolution == null) {
                throw new BadRequestException("Missing resolution for file: " + file.getPath());
            }

            String selectedContent;

            switch (resolution.getResolution()) {
                case SOURCE -> selectedContent = file.getSourceContent();

                case TARGET -> selectedContent = file.getTargetContent();

                case BOTH -> selectedContent =
                        file.getTargetContent() + System.lineSeparator() + file.getSourceContent();

                case CUSTOM -> {
                    if (resolution.getCustomContent() == null) {
                        throw new BadRequestException("Custom content is required for file: " + file.getPath());
                    }
                    selectedContent = resolution.getCustomContent();
                }

                default -> throw new BadRequestException(
                        "Invalid resolution for file " + file.getPath() +
                                ". Use SOURCE, TARGET, BOTH, or CUSTOM"
                );
            }

            file.setResolvedContent(selectedContent);
            file.setResolution(resolution.getResolution());
            file.setResolved(true);

            resolvedContentByPath.put(file.getPath(), selectedContent);
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
}
