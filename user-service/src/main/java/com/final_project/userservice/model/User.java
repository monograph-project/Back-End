package com.final_project.userservice.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class User {
    @Id
    private String id;
    private String keycloakId;
    private String userName;
    private String email;
    private String password;
    private String accountRef;
    private String active;
    @CreatedDate
    private String createdAt;
    @LastModifiedDate
    private String updatedAt;
}
