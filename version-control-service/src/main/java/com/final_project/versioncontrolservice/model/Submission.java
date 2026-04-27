package com.final_project.versioncontrolservice.model;


import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "submissions")
public class Submission {

    @Id
    private ObjectId id;

    @Field("task_id")
    @Indexed
    private ObjectId taskId;

    @Field("submitted_by")
    @Indexed
    private String submittedBy;

    @Field("submitted_at")
    private Instant submittedAt;

    private String description;

    @Field("branch_name")
    private String branchName;

    @Field("commit_hash")
    private String commitHash;

    @Field("pull_request_url")
    private String pullRequestUrl;

    private List<String> files = new ArrayList<>();

    private String status;  // "submitted", "reviewed", "revision_requested", "accepted"

    @Field("reviewed_by")
    private String reviewedBy;

    @Field("reviewed_at")
    private Instant reviewedAt;

    private Integer score;

    private String feedback;

    @Field("revision_count")
    private Integer revisionCount = 0;
}
