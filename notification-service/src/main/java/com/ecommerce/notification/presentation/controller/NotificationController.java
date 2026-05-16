package com.ecommerce.notification.presentation.controller;

import com.ecommerce.notification.application.service.NotificationService;
import com.ecommerce.notification.domain.model.Notification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notifications", description = "Notification log endpoints")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    @Operation(summary = "Get all received notifications")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationService.findAll());
    }

    @GetMapping("/type/{eventType}")
    @Operation(summary = "Get notifications by event type", description = "Types: USER_REGISTERED, ORDER_CREATED, PAYMENT_COMPLETED, PAYMENT_FAILED, STOCK_RESERVED, STOCK_FAILED, SHIPMENT_CREATED")
    public ResponseEntity<List<Notification>> getByType(@PathVariable String eventType) {
        return ResponseEntity.ok(notificationService.findByType(eventType));
    }
}
