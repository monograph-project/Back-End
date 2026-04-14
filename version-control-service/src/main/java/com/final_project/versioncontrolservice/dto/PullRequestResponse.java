package com.final_project.versioncontrolservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.model.PullRequestDocument;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PullRequestResponse(
        @JsonProperty("id") String id,
        @JsonProperty("repo_owner") String repoOwner,
        @JsonProperty("repo_name") String repoName,
        String author,
        @JsonProperty("source_branch") String sourceBranch,
        @JsonProperty("target_branch") String targetBranch,
        String title,
        String description,
        String status,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("merged_at") Instant mergedAt
) {
    public static PullRequestResponse from(PullRequestDocument d) {
        return new PullRequestResponse(
                d.getId().toHexString(),
                d.getRepoOwner(),
                d.getRepoName(),
                d.getAuthor(),
                d.getSourceBranch(),
                d.getTargetBranch(),
                d.getTitle(),
                d.getDescription(),
                d.getStatus(),
                d.getCreatedAt(),
                d.getMergedAt()
        );
    }
}
