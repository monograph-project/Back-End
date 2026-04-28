package com.final_project.notification_service.service;

import com.final_project.notification_service.websocket.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class WebSocketNotificationService {

    private SimpMessagingTemplate messagingTemplate;

    // Track active editing sessions
    private final Map<String, WebSocketEvents.LiveEditSession> activeEdits = new ConcurrentHashMap<>();

    // Track online users per repository
    private final Map<String, Map<String, Boolean>> repoViewers = new ConcurrentHashMap<>();

    /**
     * Broadcast push event to all repository watchers
     */
    public void notifyPush(String owner, String repo, WebSocketEvents.PushEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/push";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("push")
                .repository(owner + "/" + repo)
                .user(event.getPusher().getName())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Notify about pull request changes
     */
    public void notifyPullRequest(String owner, String repo, WebSocketEvents.PullRequestEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/pulls";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("pull_request")
                .repository(owner + "/" + repo)
                .user(event.getSender().getUsername())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);

        // Also notify specific users (reviewers, author)
        if (event.getPullRequest().getReviewers() != null) {
            for (String reviewer : event.getPullRequest().getReviewers()) {
                messagingTemplate.convertAndSendToUser(
                        reviewer,
                        "/queue/pull-requests",
                        message
                );
            }
        }
    }

    /**
     * Broadcast file changes for real-time collaboration
     */
    public void notifyFileChange(String owner, String repo, String branch, WebSocketEvents.FileChangeEvent event) {
        String destination = "/repo/" + owner + "/" + repo + "/files/" + branch;
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("file_change")
                .repository(owner + "/" + repo)
                .branch(branch)
                .user(event.getEditor().getUsername())
                .payload(event)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Send notification to specific user
     */
    public void sendUserNotification(String username, WebSocketEvents.NotificationEvent notification) {
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("notification")
                .user(username)
                .payload(notification)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSendToUser(
                username,
                "/queue/notifications",
                message
        );
    }

    /**
     * Broadcast repository activity
     */
    public void broadcastActivity(String owner, String repo, WebSocketEvents.RepositoryActivity activity) {
        String destination = "/repo/" + owner + "/" + repo + "/activity";
        WebSocketEvents.WebSocketMessage message = WebSocketEvents.WebSocketMessage.builder()
                .type("activity")
                .repository(owner + "/" + repo)
                .user(activity.getActor().getUsername())
                .payload(activity)
                .timestamp(Instant.now())
                .messageId(UUID.randomUUID().toString())
                .build();

        messagingTemplate.convertAndSend(destination, message);
    }

    /**
     * Start or update live editing session
     */
    public void startLiveEdit(String owner, String repo, String branch, String filePath, String username) {
        String sessionKey = owner + "/" + repo + "/" + branch + "/" + filePath;

        WebSocketEvents.LiveEditSession session = activeEdits.computeIfAbsent(sessionKey, k ->
                WebSocketEvents.LiveEditSession.builder()
                        .sessionId(UUID.randomUUID().toString())
                        .filePath(filePath)
                        .branch(branch)
                        .build()
        );

        if (!session.getParticipants().contains(username)) {
            session.getParticipants().add(username);
        }

        // Notify others that user started editing
        WebSocketEvents.CollaborationEvent collabEvent = WebSocketEvents.CollaborationEvent.builder()
                .filePath(filePath)
                .editor(username)
                .build();

        String destination = "/repo/" + owner + "/" + repo + "/collaboration/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("user_joined_edit")
                        .repository(owner + "/" + repo)
                        .user(username)
                        .payload(collabEvent)
                        .build()
        );
    }

    /**
     * Update cursor position for collaborative editing
     */
    public void updateCursorPosition(String owner, String repo, String branch,
                                     String filePath, String username,
                                     int line, int column) {
        WebSocketEvents.CollaborationEvent event = WebSocketEvents.CollaborationEvent.builder()
                .filePath(filePath)
                .editor(username)
                .cursor(WebSocketEvents.CursorPosition.builder().line(line).column(column).build())
                .build();

        String destination = "/repo/" + owner + "/" + repo + "/cursors/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("cursor_update")
                        .repository(owner + "/" + repo)
                        .user(username)
                        .payload(event)
                        .build()
        );
    }

    /**
     * Notify about merge conflicts
     */
    public void notifyMergeConflict(String owner, String repo, String branch,
                                    WebSocketEvents.MergeConflictEvent conflict) {
        String destination = "/repo/" + owner + "/" + repo + "/merge/" + branch;
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("merge_conflict")
                        .repository(owner + "/" + repo)
                        .branch(branch)
                        .payload(conflict)
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Track repository viewers (online users)
     */
    public void userJoinedRepo(String owner, String repo, String username) {
        String key = owner + "/" + repo;
        repoViewers.computeIfAbsent(key, k -> new ConcurrentHashMap<>())
                .put(username, true);

        // Broadcast updated viewer list
        broadcastViewerList(owner, repo);
    }

    public void userLeftRepo(String owner, String repo, String username) {
        String key = owner + "/" + repo;
        Map<String, Boolean> viewers = repoViewers.get(key);
        if (viewers != null) {
            viewers.remove(username);
            broadcastViewerList(owner, repo);
        }
    }

    private void broadcastViewerList(String owner, String repo) {
        String key = owner + "/" + repo;
        Map<String, Boolean> viewers = repoViewers.getOrDefault(key, new ConcurrentHashMap<>());

        String destination = "/repo/" + owner + "/" + repo + "/viewers";
        messagingTemplate.convertAndSend(destination,
                WebSocketEvents.WebSocketMessage.builder()
                        .type("viewers_update")
                        .repository(key)
                        .payload(Map.of("viewers", viewers.keySet()))
                        .timestamp(Instant.now())
                        .build()
        );
    }

    /**
     * Get active editing sessions for a repository
     */
    public Map<String, WebSocketEvents.LiveEditSession> getActiveEdits(String owner, String repo) {
        String prefix = owner + "/" + repo + "/";
        Map<String, WebSocketEvents.LiveEditSession> repoEdits = new ConcurrentHashMap<>();

        for (Map.Entry<String, WebSocketEvents.LiveEditSession> entry : activeEdits.entrySet()) {
            if (entry.getKey().startsWith(prefix)) {
                repoEdits.put(entry.getKey(), entry.getValue());
            }
        }

        return repoEdits;
    }
}
