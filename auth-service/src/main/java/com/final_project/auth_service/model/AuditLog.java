package com.final_project.auth_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * AuditLog document for tracking all user-related operations.
 */
@Document(collection = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String action;

    @Indexed
    private String entityType;

    private String entityId;

    private String description;

    private String oldValue;

    private String newValue;

    @Builder.Default
    private String status = "SUCCESS";

    private String ipAddress;

    private String userAgent;

    private String requestId;

    @CreatedDate
    private LocalDateTime createdAt;

    public static class Action {
        public static final String USER_CREATED = "USER_CREATED";
        public static final String USER_UPDATED = "USER_UPDATED";
        public static final String USER_DELETED = "USER_DELETED";
        public static final String USER_ACTIVATED = "USER_ACTIVATED";
        public static final String USER_SUSPENDED = "USER_SUSPENDED";
        public static final String USER_LOCKED = "USER_LOCKED";
        public static final String PASSWORD_CHANGED = "PASSWORD_CHANGED";
        public static final String PASSWORD_RESET = "PASSWORD_RESET";
        public static final String LOGIN_SUCCESS = "LOGIN_SUCCESS";
        public static final String LOGIN_FAILURE = "LOGIN_FAILURE";
        public static final String LOGOUT = "LOGOUT";
        public static final String PERMISSION_GRANTED = "PERMISSION_GRANTED";
        public static final String PERMISSION_REVOKED = "PERMISSION_REVOKED";
        public static final String ROLE_ASSIGNED = "ROLE_ASSIGNED";
        public static final String ROLE_REMOVED = "ROLE_REMOVED";
        public static final String EMAIL_VERIFIED = "EMAIL_VERIFIED";
        public static final String TWO_FACTOR_ENABLED = "TWO_FACTOR_ENABLED";
        public static final String TWO_FACTOR_DISABLED = "TWO_FACTOR_DISABLED";
    }

    public static class EntityType {
        public static final String USER = "USER";
        public static final String ROLE = "ROLE";
        public static final String PERMISSION = "PERMISSION";
        public static final String PROFILE = "PROFILE";
    }
}