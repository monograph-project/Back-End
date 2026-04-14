package com.final_project.versioncontrolservice.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "sessions")
public class SessionDocument {

    @Id
    private ObjectId id;

    private ObjectId userId;

    @Indexed(unique = true)
    private String token;

    private Instant createdAt;
    private Instant expiresAt;
}
