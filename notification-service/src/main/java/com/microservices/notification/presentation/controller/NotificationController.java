package com.microservices.notification.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@Tag(name = "Notification", description = "Notification Service API")
public class NotificationController {
    
    @GetMapping("/health")
    @Operation(summary = "Health check")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Notification Service is running! 🔔");
    }
    
    @GetMapping("/status")
    @Operation(summary = "Get listener status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "service", "notification-service",
            "status", "listening",
            "events", "user.registered, order.created, payment.completed, " +
                      "payment.failed, stock.reserved, stock.failed, shipment.created"
        ));
    }
}
