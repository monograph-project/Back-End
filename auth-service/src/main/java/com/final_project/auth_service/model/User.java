package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * User document representing a user in the system.
 */
@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @Indexed(unique = true, sparse = true)
    private String keycloakId;

    private String phoneNumber;

    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Builder.Default
    private Boolean emailVerified = false;

    @Builder.Default
    private Boolean twoFactorEnabled = false;

    private LocalDateTime lastLogin;

    private String lastLoginIp;

    private LocalDateTime lastPasswordChange;

    @Builder.Default
    private Integer failedLoginAttempts = 0;

    private LocalDateTime lockedUntil;

    private String password;

    /**
     * Store role IDs instead of ManyToMany.
     */
    @Builder.Default
    private Set<String> roleIds = new HashSet<>();

    /**
     * Option 1: store profile as separate document ID.
     */
    private String profileId;

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private String createdBy;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private String updatedBy;

    private LocalDateTime deletedAt;

    private String deletedBy;


    public enum UserStatus {
        ACTIVE,
        INACTIVE,
        SUSPENDED,
        DELETED
    }

    public boolean isAccountLocked() {
        return lockedUntil != null && LocalDateTime.now().isBefore(lockedUntil);
    }

    public boolean isEnabled() {
        return status == UserStatus.ACTIVE && !isAccountLocked();
    }

    public String getFullName() {
        String first = firstName != null ? firstName : "";
        String last = lastName != null ? lastName : "";
        return (first + " " + last).trim();
    }
}