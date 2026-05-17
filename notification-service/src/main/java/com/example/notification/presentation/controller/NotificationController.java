package com.example.notification.presentation.controller;

import com.example.notification.application.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public String sendNotification(@RequestParam String username) {
        notificationService.sendNotification(username);
        return "Notification sent to: " + username;
    }
}
