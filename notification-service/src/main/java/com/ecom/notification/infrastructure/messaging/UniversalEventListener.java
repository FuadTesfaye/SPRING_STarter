package com.ecom.notification.infrastructure.messaging;

import com.ecom.notification.application.service.NotificationHandler;

public class UniversalEventListener {
    private final NotificationHandler notificationHandler;

    public UniversalEventListener(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    public void handleMessage(String routingKey, String body) {
        notificationHandler.processEvent(routingKey, body);
    }
}
