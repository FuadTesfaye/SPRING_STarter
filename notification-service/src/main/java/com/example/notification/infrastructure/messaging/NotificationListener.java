package com.example.notification.infrastructure.messaging;

import com.example.notification.application.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.SHIPPING_CREATED_QUEUE)
    public void handleShippingCreated(String username) {
        System.out.println("Notification-service received shipping.created for: " + username);
        notificationService.sendNotification(username);
    }
}
