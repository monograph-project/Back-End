package com.final_project.versioncontrolservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.final_project.versioncontrolservice.model.InvitationDocument;

import java.time.Instant;

public record InvitationResponse(
        @JsonProperty("id") String id,
        @JsonProperty("repo_owner") String repoOwner,
        @JsonProperty("repo_name") String repoName,
        @JsonProperty("invited_user") String invitedUser,
        String role,
        String status,
        @JsonProperty("created_at") Instant createdAt
) {
    public static InvitationResponse from(InvitationDocument d) {
        return new InvitationResponse(
                d.getId().toHexString(),
                d.getRepoOwner(),
                d.getRepoName(),
                d.getInvitedUser(),
                d.getRole(),
                d.getStatus(),
                d.getCreatedAt()
        );
    }
}
