package com.company.notification.infrastructure.messaging.rabbitmq;

import com.company.notification.domain.event.UserRegisteredEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @RabbitListener(queues = "notification.queue")
    public void handleUserRegistered(UserRegisteredEvent event) {
        System.out.println("Notification Service RECEIVED User: " + event.getUsername());
        System.out.println("Sending welcome email to " + event.getEmail());
        // Business logic would go here
    }
}
