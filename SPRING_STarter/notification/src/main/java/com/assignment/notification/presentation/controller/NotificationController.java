package com.assignment.notification.presentation.controller;

import com.assignment.notification.application.dto.LogRequest;
import com.assignment.notification.application.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    
    private final NotificationService notificationService;
    
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    
    @PostMapping("/log")
    public ResponseEntity<Map<String, String>> logEvent(@RequestBody LogRequest request) {
        notificationService.logEvent(request);
        
        Map<String, String> response = new HashMap<>();
        response.put("status", "logged");
        return ResponseEntity.ok(response);
    }
}