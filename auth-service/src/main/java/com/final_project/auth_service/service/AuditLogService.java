package com.final_project.auth_service.service;
import com.final_project.auth_service.model.AuditLog;
import com.final_project.auth_service.repository.AuditLogRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

/**
 * Service for audit logging operations.
 *
 * Handles:
 * - Logging user actions
 * - Recording authentication events
 * - Tracking permission changes
 * - Storing audit trails asynchronously
 */
@Service
@Slf4j
@AllArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    /**
     * Log an audit event asynchronously.
     *
     * @param userId User ID performing the action
     * @param action Action name
     * @param entityType Entity type affected
     * @param entityId Entity ID affected
     * @param description Action description
     * @param status Operation status
     */
    @Async
    @Transactional
    public void logAuditEvent(String userId, String action, String entityType, String entityId, String description, String status) {
        logAuditEvent(userId, action, entityType, entityId, description, null, null, status);
    }

    /**
     * Log an audit event with old and new values.
     *
     * @param userId User ID performing the action
     * @param action Action name
     * @param entityType Entity type affected
     * @param entityId Entity ID affected
     * @param description Action description
     * @param oldValue Previous value
     * @param newValue New value
     * @param status Operation status
     */
    @Async
    @Transactional
    public void logAuditEvent(String userId, String action, String entityType, String entityId, String description, String oldValue, String newValue, String status) {
        try {
            String ipAddress = getClientIpAddress();
            String userAgent = getUserAgent();
            String requestId = getRequestId();

            AuditLog auditLog = AuditLog.builder()
                    .userId(userId)
                    .action(action)
                    .entityType(entityType)
                    .entityId(entityId)
                    .description(description)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .status(status)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .requestId(requestId)
                    .createdAt(LocalDateTime.now())
                    .build();

            auditLogRepository.save(auditLog);
            log.debug("Audit log saved: {} - {} - {}", userId, action, entityId);

        } catch (Exception e) {
            log.error("Failed to save audit log: {} - {} - {}", userId, action, entityId, e);
        }
    }

    /**
     * Get audit logs for a specific user.
     *
     * @param userId User ID
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getUserAuditLogs(String userId, Pageable pageable) {
        return auditLogRepository.findByUserId(userId, pageable);
    }

    /**
     * Get audit logs for a specific action.
     *
     * @param action Action name
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getActionAuditLogs(String action, Pageable pageable) {
        return auditLogRepository.findByAction(action, pageable);
    }

    /**
     * Get audit logs for a specific entity.
     *
     * @param entityType Entity type
     * @param entityId Entity ID
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getEntityAuditLogs(String entityType, String entityId, Pageable pageable) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable);
    }

    /**
     * Get failed audit operations.
     *
     * @param pageable Pagination info
     * @return Page of failed audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getFailedOperations(Pageable pageable) {
        return auditLogRepository.findFailedOperations(pageable);
    }

    /**
     * Get audit logs by date range.
     *
     * @param startDate Start date
     * @param endDate End date
     * @param pageable Pagination info
     * @return Page of audit logs
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return auditLogRepository.findByCreatedAtBetween(startDate, endDate, pageable);
    }

    /**
     * Get client IP address from request.
     *
     * @return Client IP address
     */
    private String getClientIpAddress() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                String ipAddress = attributes.getRequest().getHeader("X-Forwarded-For");
                if (ipAddress == null || ipAddress.isEmpty()) {
                    ipAddress = attributes.getRequest().getRemoteAddr();
                }
                return ipAddress;
            }
        } catch (Exception e) {
            log.debug("Could not get client IP address", e);
        }
        return "UNKNOWN";
    }

    /**
     * Get user agent from request.
     *
     * @return User agent
     */
    private String getUserAgent() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest().getHeader("User-Agent");
            }
        } catch (Exception e) {
            log.debug("Could not get user agent", e);
        }
        return "UNKNOWN";
    }

    /**
     * Get request ID from request.
     *
     * @return Request ID
     */
    private String getRequestId() {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                return attributes.getRequest().getHeader("X-Request-ID");
            }
        } catch (Exception e) {
            log.debug("Could not get request ID", e);
        }
        return null;
    }
}