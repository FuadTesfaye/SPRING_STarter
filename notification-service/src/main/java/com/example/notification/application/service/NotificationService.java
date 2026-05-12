package com.microservices.notificationservice.application.service;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void sendNotification(String username) {

        System.out.println(
                "Notification sent to: " + username
        );
    }
}