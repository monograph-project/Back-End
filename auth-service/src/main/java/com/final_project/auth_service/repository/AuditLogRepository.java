package com.final_project.auth_service.repository;

import com.final_project.auth_service.model.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    Page<AuditLog> findByUserId(String userId, Pageable pageable);

    Page<AuditLog> findByAction(String action, Pageable pageable);

    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    Page<AuditLog> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<AuditLog> findByStatus(String status, Pageable pageable);

    default Page<AuditLog> findFailedOperations(Pageable pageable) {
        return findByStatus("FAILURE", pageable);
    }

    long countByAction(String action);

    long countByStatusAndCreatedAtAfter(String status, LocalDateTime after);
}