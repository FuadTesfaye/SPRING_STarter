package com.example.notificationsservices.infrastructure.messaging;

import com.example.notificationsservices.infrastructure.messaging.dto.EventMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @RabbitListener(queues = "notification.queue")
    public void handle(EventMessage event) {

        System.out.println("========== NOTIFICATION ==========");
        System.out.println("TYPE: " + event.getType());
        System.out.println("TO: " + event.getRecipient());
        System.out.println("MESSAGE: " + event.getMessage());
        System.out.println("==================================");
    }
}