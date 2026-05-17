package com.example.notificationservice.application.service;


import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void notifyUser(Object event, String eventType) {
        System.out.println("=".repeat(70));
        System.out.println("📧 NOTIFICATION SERVICE");
        System.out.println("Event Type : " + eventType);
        System.out.println("Timestamp  : " + java.time.LocalDateTime.now());
        System.out.println("Payload    : " + event);
        System.out.println("=".repeat(70));
    }
}