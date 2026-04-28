package com.final_project.versioncontrolservice.model;

public enum PullRequestStatus {
    OPENED("opened"),
    CLOSED("closed"),
    MERGED("merge"),
    DRAFT("draft"),
    READY_FOR_REVIEW("ready_for_review"),
    CONFLICTED("conflicted");

    private String status;
    PullRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
