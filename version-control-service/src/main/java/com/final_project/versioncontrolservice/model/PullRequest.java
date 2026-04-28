package com.final_project.versioncontrolservice.model;

import com.final_project.versioncontrolservice.dto.PullRequestUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@AllArgsConstructor
@Builder
@Document(collection = "pull_request")
public class PullRequest {
    @Id
    private String id;
    @Field("repo_owner")
    private PullRequestUser repoOwner;
    @Field("repo_name")
    private String repoName;

    private PullRequestUser author;
    @Field("source_branch")
    private String sourceBranch;
    @Field("target_branch")
    private String targetBranch;
    private String title;
    private String description;
    private PullRequestStatus status;
    @CreatedDate
    private Instant createdAt;
    @Field("merged_at")
    private Instant mergedAt;
}
