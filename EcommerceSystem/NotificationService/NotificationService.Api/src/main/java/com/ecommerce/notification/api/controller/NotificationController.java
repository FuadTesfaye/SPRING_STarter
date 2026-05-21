package com.ecommerce.notification.api.controller;

import com.ecommerce.notification.infrastructure.messaging.NotificationLogStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @GetMapping("/logs")
    public List<String> getLogs() {
        return NotificationLogStore.NOTIFICATION_LOGS;
    }
}
