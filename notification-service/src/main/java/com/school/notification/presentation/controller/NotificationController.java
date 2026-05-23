package com.school.notification.presentation.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notifications", description = "Notification service - listens to all events")
public class NotificationController {

    @GetMapping("/health")
    public String health() {
        return "Notification Service is running on port 8086. Listening to ALL events via RabbitMQ.";
    }
}
