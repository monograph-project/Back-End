package com.final_project.versioncontrolservice.service;

import com.final_project.versioncontrolservice.exception.BadRequestException;
import com.final_project.versioncontrolservice.exception.ForbiddenException;
import com.final_project.versioncontrolservice.exception.NotFoundException;
import com.final_project.versioncontrolservice.repo.PullRequestRepository;
import com.final_project.versioncontrolservice.model.PullRequestDocument;
import com.final_project.versioncontrolservice.model.RepositoryDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PullRequestApplicationService {

    private final PullRequestRepository pullRequestRepository;
    private final RepositoryService vicRepositoryService;
    private final CommitGraphService commitGraphService;

    public PullRequestApplicationService(
            PullRequestRepository pullRequestRepository,
            RepositoryService vicRepositoryService,
            CommitGraphService commitGraphService
    ) {
        this.pullRequestRepository = pullRequestRepository;
        this.vicRepositoryService = vicRepositoryService;
        this.commitGraphService = commitGraphService;
    }

    public PullRequestDocument create(
            RepositoryDocument meta,
            String author,
            String sourceBranch,
            String targetBranch,
            String title,
            String description
    ) {
        if (!RepoAccessRules.canRead(meta, author)) {
            throw new ForbiddenException("forbidden");
        }
        sourceBranch = sourceBranch.trim();
        targetBranch = targetBranch.trim();
        title = title.trim();
        description = description == null ? "" : description.trim();
        if (sourceBranch.isEmpty() || targetBranch.isEmpty() || title.isEmpty()) {
            throw new BadRequestException("source_branch, target_branch and title are required");
        }
        if (sourceBranch.equals(targetBranch)) {
            throw new BadRequestException("source and target branch cannot be the same");
        }
        String headSource = vicRepositoryService.listBranchHash(meta, sourceBranch);
        if (headSource.isEmpty()) {
            throw new BadRequestException("source branch \"" + sourceBranch + "\" does not exist");
        }
        String headTarget = vicRepositoryService.listBranchHash(meta, targetBranch);
        if (headTarget.isEmpty()) {
            throw new BadRequestException("target branch \"" + targetBranch + "\" does not exist");
        }

        PullRequestDocument pr = new PullRequestDocument();
        pr.setRepoOwner(meta.getOwner().getUsername());
        pr.setRepoName(meta.getRepositoryName());
        pr.setAuthor(author.trim().toLowerCase());
        pr.setSourceBranch(sourceBranch);
        pr.setTargetBranch(targetBranch);
        pr.setTitle(title);
        pr.setDescription(description);
        pr.setStatus("open");
        pr.setCreatedAt(Instant.now());
        return pullRequestRepository.save(pr);
    }

    public List<PullRequestDocument> list(RepositoryDocument meta) {
        return pullRequestRepository.findByRepoOwnerAndRepoName(meta.getOwner().getUsername(), meta.getRepositoryName());
    }

    public PullRequestDocument find(RepositoryDocument meta, String idHex) {
        ObjectId id;
        try {
            id = new ObjectId(idHex.trim());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("invalid pull request id");
        }
        return pullRequestRepository
                .findByIdAndRepoOwnerAndRepoName(id, meta.getOwner().getUsername(), meta.getRepositoryName())
                .orElseThrow(() -> new NotFoundException("pull request not found"));
    }

    public void merge(RepositoryDocument meta, PullRequestDocument pr, String adminUsername) {
        if (!RepoAccessRules.canAdmin(meta, adminUsername)) {
            throw new ForbiddenException("forbidden");
        }
        if (!"open".equals(pr.getStatus())) {
            throw new BadRequestException("pull request is not open");
        }
        String sourceHash = vicRepositoryService.listBranchHash(meta, pr.getSourceBranch());
        if (sourceHash.isEmpty()) {
            throw new BadRequestException("source branch does not exist");
        }
        String targetHash = vicRepositoryService.listBranchHash(meta, pr.getTargetBranch());
        if (targetHash.isEmpty()) {
            throw new BadRequestException("target branch does not exist");
        }

        if (sourceHash.equals(targetHash)) {
            markMerged(pr.getId());
            return;
        }

        boolean canFf;
        try {
            canFf = commitGraphService.isAncestorInRepo(meta.getOwner().getUsername(), meta.getRepositoryName(), targetHash, sourceHash);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        if (!canFf) {
            throw new BadRequestException("non-fast-forward merge not supported yet");
        }

        RepositoryDocument fresh = vicRepositoryService.loadMeta(meta.getOwner().getUsername(), meta.getRepositoryName());
        vicRepositoryService.updateBranchRef(fresh, pr.getTargetBranch(), sourceHash);
        markMerged(pr.getId());
    }

    private void markMerged(ObjectId id) {
        PullRequestDocument pr = pullRequestRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("pull request not found"));
        pr.setStatus("merged");
        pr.setMergedAt(Instant.now());
        pullRequestRepository.save(pr);
    }
}
