package com.final_project.versioncontrolservice.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "pull_request_conflicts")
public class PullRequestConflict {
    @Id
    private String id;
    private String pullRequestId;
    private String owner;
    private String repoName;

    private String sourceBranch;
    private String targetBranch;

    private String baseHash;
    private String sourceHash;
    private String targetHash;

    private String baseTreeHash;
    private String sourceTreeHash;
    private String targetTreeHash;

    private List<ConflictFile> files;

    private boolean resolved;

    private Instant createdAt;
    private Instant resolvedAt;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConflictFile {
        private String path;

        private String baseHash;
        private String sourceHash;
        private String targetHash;

        private String baseContent;
        private String sourceContent;
        private String targetContent;

        private String resolvedContent;
        private ConflictResolution resolution;
        private boolean resolved;
    }

    public enum ConflictResolution {
        SOURCE,
        TARGET,
        BOTH,
        CUSTOM
    }
}