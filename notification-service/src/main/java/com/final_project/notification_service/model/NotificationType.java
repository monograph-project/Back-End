package com.final_project.notification_service.model;
public enum NotificationType {
    // Account lifecycle
    USER_REGISTERED,
    PASSWORD_CHANGED,
    EMAIL_VERIFIED,
    ACCOUNT_LOCKED,

    // Invitations
    SYSTEM_INVITATION,
    REPOSITORY_INVITATION,

    // Blog interactions
    BLOG_NEW_COMMENT,
    BLOG_COMMENT_REPLY,
    BLOG_POST_PUBLISHED,

    // Generic
    CUSTOM
}
