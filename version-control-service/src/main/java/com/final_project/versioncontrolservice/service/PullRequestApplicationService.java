package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.dto.*;
import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.model.PullRequest;
import com.final_project.versioncontrolservice.model.PullRequestStatus;
import com.final_project.versioncontrolservice.repo.PullRequestRepository;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class PullRequestApplicationService {

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
            canFf = commitGraphService.isAncestorInRepo(document.getOwner().getUsername(), document.getRepositoryName(), targetHash, sourceHash);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        if (!canFf) {
            throw new BadRequestException("non-fast-forward merge not supported yet");
        }

        RepositoryDocument mergedRepository = repositoryService.loadMeta(document.getOwner().getUsername(), document.getRepositoryName());
        repositoryService.updateBranchRef(mergedRepository, pullRequest.getTargetBranch(), sourceHash);
        markMerged(pullRequest.getId());

        return MergeResponse
                .builder()
                .mergedAt(Instant.now())
                .pullRequestId(pullRequest.getId())
                .status(PullRequestStatus.MERGED)
                .targetBranch(pullRequest.getTargetBranch())
                .sourceBranch(pullRequest.getSourceBranch())
                .newHead(sourceHash)
                .message("Success")
                .build();
    }

    private void markMerged(String  id) {
        PullRequest pr = pullRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("pull request not found"));
        pr.setStatus(PullRequestStatus.MERGED);
        pr.setMergedAt(Instant.now());
        pullRequestRepository.save(pr);
    }
}
