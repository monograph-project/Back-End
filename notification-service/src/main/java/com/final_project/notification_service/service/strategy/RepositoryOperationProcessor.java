package com.final_project.notification_service.service.strategy;

import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.RepositoryOperationEvent;
import com.final_project.notification_service.model.*;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class RepositoryOperationProcessor implements NotificationProcessor<RepositoryOperationEvent> {

    private final NotificationService notificationService;
    @Override
    public void process(RepositoryOperationEvent event) {
        switch (event.getEventType()) {

            case REPOSITORY_PUSHED, COMMIT_CREATED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PUSH,
                    safe(event.getActorName()) + " pushed changes to " + safe(event.getRepositoryName()),
                    "Repository push notification sent."
            );

            case REPOSITORY_INVITATION_SENT -> notifyInvitedUser(event);

            case REPOSITORY_INVITATION_ACCEPTED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_INVITATION_ACCEPTED,
                    safe(event.getInvitedUserName()) + " accepted repository invitation",
                    "Repository invitation accepted notification sent."
            );

            case BRANCH_CREATED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_BRANCH_CREATED,
                    safe(event.getActorName()) + " created branch " + safe(event.getBranchName()),
                    "Repository branch created notification sent."
            );

            case BRANCH_MERGED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_BRANCH_MERGED,
                    safe(event.getActorName()) + " merged " + safe(event.getSourceBranch()) + " into " + safe(event.getTargetBranch()),
                    "Repository branch merged notification sent."
            );

            case PULL_REQUEST_OPENED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PULL_REQUEST_OPENED,
                    safe(event.getActorName()) + " opened a pull request in " + safe(event.getRepositoryName()),
                    "Pull request opened notification sent."
            );

            case PULL_REQUEST_MERGED -> notifyRepositoryMembers(
                    event,
                    NotificationType.REPOSITORY_PULL_REQUEST_MERGED,
                    safe(event.getActorName()) + " merged a pull request in " + safe(event.getRepositoryName()),
                    "Pull request merged notification sent."
            );

            case REPOSITORY_PULLED, REPOSITORY_FETCHED, REPOSITORY_CLONED -> {
                log.debug("No notification needed for eventType={}", event.getEventType());
            }

            default -> log.warn("Unhandled repository eventType={}", event.getEventType());
        }
    }

    private void notifyRepositoryMembers(
            RepositoryOperationEvent event,
            NotificationType notificationType,
            String subject,
            String body
    ) {
        List<RepositoryMemberRecipient> recipients = event.getRecipients();

        if (recipients == null || recipients.isEmpty()) {
            log.warn("No recipients found for repository eventId={}", event.getEventId());
            return;
        }

        for (RepositoryMemberRecipient recipient : recipients) {

            if (isActor(event, recipient)) {
                log.debug("Skipping actor notification for userId={}", recipient.getUserId());
                continue;
            }
            saveNotification(
                    event,
                    recipient,
                    notificationType,
                    subject,
                    body,
                    event.getRepositoryId(),
                    "REPOSITORY"
            );
        }
    }

    private void notifyInvitedUser(RepositoryOperationEvent event) {
        if (event.getInvitedUserId() != null &&
                event.getInvitedUserId().equals(event.getActorUserId())) {
            log.debug("Skipping self invitation notification userId={}", event.getActorUserId());
            return;
        }

        String subject = safe(event.getActorName())
                + " invited you to repository "
                + safe(event.getRepositoryName());

        RepositoryMemberRecipient recipient = RepositoryMemberRecipient.builder()
                .userId(event.getInvitedUserId())
                .name(event.getInvitedUserName())
                .email(event.getInvitedUserEmail())
                .role("INVITED")
                .build();
        saveNotification(
                event,
                recipient,
                NotificationType.REPOSITORY_INVITATION_SENT,
                subject,
                "Repository invitation notification sent.",
                event.getRepositoryId(),
                "REPOSITORY"
        );
    }
    private void saveNotification(
            RepositoryOperationEvent event,
            RepositoryMemberRecipient recipient,
            NotificationType type,
            String subject,
            String body,
            String referenceId,
            String referenceType
    ) {
        Notification notification = Notification.builder()
                .recipientUserId(recipient.getUserId())
                .recipientEmail(recipient.getEmail())
                .recipientName(recipient.getName())
                .type(type)
                .channel(NotificationChannel.IN_APP)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body(body)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .idempotencyKey(event.getEventType() + ":" + event.getEventId() + ":" + recipient.getUserId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    private boolean isActor(RepositoryOperationEvent event, RepositoryMemberRecipient recipient) {
        return recipient.getUserId() != null
                && recipient.getUserId().equals(event.getActorUserId());
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    @Override
    public Class<RepositoryOperationEvent> supportedEventType() {
        return RepositoryOperationEvent.class;
    }
}