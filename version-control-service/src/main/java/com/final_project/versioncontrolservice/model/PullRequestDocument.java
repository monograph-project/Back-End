package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Document(collection = "pull_requests")
public class PullRequestDocument {

    @Id
    private ObjectId id;

    @Field("repo_owner")
    private String repoOwner;

    @Field("repo_name")
    private String repoName;

    private String author;

    @Field("source_branch")
    private String sourceBranch;

    @Field("target_branch")
    private String targetBranch;

    private String title;
    private String description;
    private String status;
    private Instant createdAt;

    @Field("merged_at")
    private Instant mergedAt;
}
