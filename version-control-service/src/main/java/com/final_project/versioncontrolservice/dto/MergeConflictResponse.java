package com.final_project.versioncontrolservice.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder
public class MergeConflictResponse {
    private String pullRequestId;
    private String status;
    private List<ConflictFileDTO> conflicts;

    @Data
    @Builder
    public static class ConflictFileDTO {
        private String path;
        private String baseContent;
        private String sourceContent;
        private String targetContent;
        private boolean binary;
    }
}