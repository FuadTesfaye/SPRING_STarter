package com.assignment.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
        System.out.println("📢 Notification Service running on http://localhost:8086");
        System.out.println("📝 H2 Console: http://localhost:8086/h2-console");
    }
}