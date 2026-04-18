package com.final_project.notification_service.service;


import com.final_project.notification_service.dto.request.ResendNotificationRequest;
import com.final_project.notification_service.dto.request.SendNotificationRequest;
import com.final_project.notification_service.dto.response.NotificationResponse;
import com.final_project.notification_service.dto.response.NotificationStatsResponse;
import com.final_project.notification_service.dto.response.PagedResponse;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationService {

    // ── Sending ──────────────────────────────────────────────────────────────

    NotificationResponse send(SendNotificationRequest request);

    NotificationResponse resend(ResendNotificationRequest request);

    // ── Querying ─────────────────────────────────────────────────────────────

    NotificationResponse findById(UUID id);

    PagedResponse<NotificationResponse> findByUser(String userId, Pageable pageable);

    PagedResponse<NotificationResponse> findByUserAndType(
            String userId, NotificationType type, Pageable pageable);

    PagedResponse<NotificationResponse> findByUserAndStatus(
            String userId, NotificationStatus status, Pageable pageable);

    PagedResponse<NotificationResponse> findAll(Pageable pageable);

    PagedResponse<NotificationResponse> findByStatus(NotificationStatus status, Pageable pageable);

    PagedResponse<NotificationResponse> findByType(NotificationType type, Pageable pageable);

    PagedResponse<NotificationResponse> findByReference(
            String referenceId, String referenceType, Pageable pageable);

    // ── Stats ────────────────────────────────────────────────────────────────

    NotificationStatsResponse getStats(LocalDateTime from, LocalDateTime to);

    long countUnreadByUser(String userId);

    // ── Admin operations ─────────────────────────────────────────────────────

    void retryFailedNotifications();

    void deleteOldSentNotifications(LocalDateTime before);

    // ── Internal (used by processors) ────────────────────────────────────────

    Notification saveAndProcess(Notification notification);
}