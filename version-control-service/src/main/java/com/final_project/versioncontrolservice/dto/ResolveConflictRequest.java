package com.final_project.versioncontrolservice.dto;

import com.final_project.versioncontrolservice.model.PullRequestConflict;
import lombok.Data;

import java.util.List;

@Data
public class ResolveConflictRequest {
    private List<FileResolution> files;

    @Data
    public static class FileResolution {
        private String path;
        // SOURCE, TARGET, BOTH, CUSTOM
        private PullRequestConflict.ConflictResolution resolution;

        // required only when resolution = CUSTOM
        private String customContent;
    }
}