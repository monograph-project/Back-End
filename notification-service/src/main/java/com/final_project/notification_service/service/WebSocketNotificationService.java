package com.final_project.notification_service.service;

import com.final_project.notification_service.dto.mapper.NotificationMapper;
import com.final_project.notification_service.model.Notification;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WebSocketNotificationService {

    private SimpMessagingTemplate messagingTemplate;
    private NotificationMapper mapper;
    public void sendToUser(Notification notification) {
        messagingTemplate.convertAndSendToUser(
                notification.getRecipientUserId(),
                "/queue/notifications",
                mapper.toResponse(notification)
        );
    }
}
