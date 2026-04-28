package com.final_project.notification_service.service.strategy;
import com.final_project.notification_service.config.AppProperties;
import com.final_project.notification_service.event.BlogInteractionEvent;
import com.final_project.notification_service.model.Notification;
import com.final_project.notification_service.model.NotificationChannel;
import com.final_project.notification_service.model.NotificationStatus;
import com.final_project.notification_service.model.NotificationType;
import com.final_project.notification_service.service.EmailService;
import com.final_project.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BlogInteractionProcessor implements NotificationProcessor<BlogInteractionEvent> {

    private final NotificationService notificationService;
    private final EmailService emailService;
    private final AppProperties props;

    @Override
    public void process(BlogInteractionEvent event) {

        if (isSelfAction(event)) {
            log.debug("Skipping self notification for userId={}", event.getActorUserId());
            return;
        }

        switch (event.getEventType()) {
            case COMMENT_REPLIED -> handleCommentReplied(event);

            case COMMENT_CREATED -> processBlogCommented(event);

            case ARTICLE_LIKED -> processBlogLiked(event);

            case ARTICLE_SHARED -> processBlogShared(event);

            case ARTICLE_PUBLISHED -> processBlogPublished(event);
            case ARTICLE_UNLIKED, COMMENT_DELETED -> {
                log.debug("No notification needed for eventType={}", event.getEventType());
            }
            default -> log.warn("Unhandled eventType={}", event.getEventType());
        }
    }

    private void handleCommentReplied(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_COMMENT_REPLY,
                event.getActorName() + " replied to your comment on: " + event.getBlogPostTitle(),
                event.getCommentSnippet(),
                event.getCommentId(),
                "COMMENT"
        );
    }

    private void processBlogCommented(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_NEW_COMMENT,
                event.getActorName() + " commented on your post: " + event.getBlogPostTitle(),
                "Blog comment notification sent.",
                event.getCommentId(),
                "COMMENT"
        );
    }

    private void processBlogLiked(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_LIKED,
                event.getActorName() + " liked your post: " + event.getBlogPostTitle(),
                "Blog like notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void processBlogShared(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_SHARED,
                event.getActorName() + " shared your post: " + event.getBlogPostTitle(),
                "Blog share notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void processBlogPublished(BlogInteractionEvent event) {
        sendArticleNotification(
                event,
                NotificationType.BLOG_POST_PUBLISHED,
                "Your blog post has been published: " + event.getBlogPostTitle(),
                "Blog published notification sent.",
                event.getBlogPostId(),
                "BLOG_POST"
        );
    }

    private void sendArticleNotification(
            BlogInteractionEvent event,
            NotificationType notificationType,
            String subject,
            String notificationBody,
            String referenceId,
            String referenceType
    ) {
        emailService.sendHtmlEmail(
                event.getAuthorEmail(),
                subject,
                "article-notification",
                buildArticleTemplateModel(event)
        );

        saveNotification(
                event,
                notificationType,
                subject,
                notificationBody,
                referenceId,
                referenceType
        );
    }

    private Map<String, Object> buildArticleTemplateModel(BlogInteractionEvent event) {
        String headerTitle;
        String headerSubtitle;
        String mainMessage;
        String actionText;
        String messageBoxTitle;

        switch (event.getEventType()) {
            case COMMENT_CREATED -> {
                headerTitle = "New Comment";
                headerSubtitle = "Someone commented on your article";
                mainMessage = safe(event.getActorName()) + " commented on your article.";
                actionText = "View Comment";
                messageBoxTitle = "Comment";
            }
            case COMMENT_REPLIED -> {
                headerTitle = "New Reply";
                headerSubtitle = "Someone replied to your comment";
                mainMessage = safe(event.getActorName()) + " replied to your comment.";
                actionText = "View Reply";
                messageBoxTitle = "Reply";
            }
            case ARTICLE_LIKED -> {
                headerTitle = "Article Liked";
                headerSubtitle = "Someone liked your article";
                mainMessage = safe(event.getActorName()) + " liked your article.";
                actionText = "Open Article";
                messageBoxTitle = "Activity";
            }
            case ARTICLE_SHARED -> {
                headerTitle = "Article Shared";
                headerSubtitle = "Someone shared your article";
                mainMessage = safe(event.getActorName()) + " shared your article"
                        + (event.getSharePlatform() != null ? " on " + event.getSharePlatform() : "")
                        + ".";
                actionText = "Open Article";
                messageBoxTitle = "Share";
            }
            case ARTICLE_PUBLISHED -> {
                headerTitle = "Article Published";
                headerSubtitle = "Your article is now public";
                mainMessage = "Your article was approved and published.";
                actionText = "View Published Article";
                messageBoxTitle = "Publication";
            }
            default -> {
                headerTitle = "Article Update";
                headerSubtitle = "There is new activity on your article";
                mainMessage = "There is a new update related to your article.";
                actionText = "Open Article";
                messageBoxTitle = "Details";
            }
        }

        return Map.ofEntries(
                Map.entry("emailTitle", headerTitle),
                Map.entry("eventType", event.getEventType().name()),
                Map.entry("headerTitle", headerTitle),
                Map.entry("headerSubtitle", headerSubtitle),
                Map.entry("recipientName", safe(event.getAuthorName())),
                Map.entry("mainMessage", mainMessage),
                Map.entry("postTitle", safe(event.getBlogPostTitle())),
                Map.entry("postUrl", safe(event.getBlogPostUrl())),
                Map.entry("actionUrl", safe(event.getBlogPostUrl())),
                Map.entry("actionText", actionText),
                Map.entry("actorName", safe(event.getActorName())),
                Map.entry("actorEmail", safe(event.getActorEmail())),
                Map.entry("commentSnippet", truncate(event.getCommentSnippet(), 200)),
                Map.entry("messageBoxTitle", messageBoxTitle),
                Map.entry("sharePlatform", safe(event.getSharePlatform())),
                Map.entry("adminName", safe(event.getAdminName())),
                Map.entry("profile", safe(event.getProfile())),
                Map.entry("occurredAt", event.getOccurredAt() == null ? "" : event.getOccurredAt().toString()),
                Map.entry("baseUrl", props.getNotification().getBaseUrl())
        );
    }

    private void saveNotification(
            BlogInteractionEvent event,
            NotificationType type,
            String subject,
            String body,
            String referenceId,
            String referenceType
    ) {
        Notification notification = Notification.builder()
                .recipientUserId(event.getAuthorUserId())
                .recipientEmail(event.getAuthorEmail())
                .recipientName(event.getAuthorName())
                .type(type)
                .channel(NotificationChannel.EMAIL)
                .status(NotificationStatus.PROCESSING)
                .subject(subject)
                .body(body)
                .referenceId(referenceId)
                .referenceType(referenceType)
                .idempotencyKey(event.getEventType() + ":" + event.getEventId())
                .build();

        notificationService.saveAndProcess(notification);
    }

    private boolean isSelfAction(BlogInteractionEvent event) {
        return event.getAuthorUserId() != null
                && event.getAuthorUserId().equals(event.getActorUserId());
    }

    @Override
    public Class<BlogInteractionEvent> supportedEventType() {
        return BlogInteractionEvent.class;
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        return text.length() <= max ? text : text.substring(0, max) + "…";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}