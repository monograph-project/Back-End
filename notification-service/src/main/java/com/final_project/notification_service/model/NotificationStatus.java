package com.final_project.notification_service.model;

public enum NotificationStatus {
    PENDING,
    PROCESSING,
    SENT,
    FAILED,
    RETRYING,
    SKIPPED     // idempotency hit — already processed
}