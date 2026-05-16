package com.example.task.notification;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    // This method automatically prints out any event sent across the app
    @RabbitListener(queues = "notificationQueue")
    public void handleAllEvents(String message) {
        System.out.println("[BROADCAST RECEIVED]: " + message);
    }
}

