package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Data
@Document(collection = "invitations")
public class InvitationDocument {

    @Id
    private ObjectId id;

    @Field("repo_owner")
    private String repoOwner;

    @Field("repo_name")
    private String repoName;

    @Field("invited_user")
    private String invitedUser;

    private String role;
    private String status;
    private Instant createdAt;
}
