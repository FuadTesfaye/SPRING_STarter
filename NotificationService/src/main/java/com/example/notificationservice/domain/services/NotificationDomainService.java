package com.example.notificationservice.domain.services;

import com.example.notificationservice.domain.entities.Notification;
import com.example.notificationservice.domain.enums.NotificationStatus;
import java.util.UUID;

public class NotificationDomainService {

    public Notification createNotification(String orderId, Long productId, String message) {
        return new Notification(
                UUID.randomUUID().toString(),
                orderId,
                productId,
                message,
                NotificationStatus.NOTIFICATION_SENT
        );
    }
}
