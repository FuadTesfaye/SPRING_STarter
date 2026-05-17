package com.example.notification.presentation.controller;

import com.example.notification.application.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notifications", description = "Notification endpoints")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Operation(summary = "Send a notification to a user",
        responses = {
            @ApiResponse(responseCode = "200", description = "Notification sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
        })
    @PostMapping("/send")
    public String sendNotification(@RequestParam(name = "username") String username) {
        notificationService.sendNotification(username);
        return "Notification sent to: " + username;
    }

    @Operation(summary = "Check notification service status")
    @GetMapping
    public String status() {
        return "Notification Service Running";
    }
}
