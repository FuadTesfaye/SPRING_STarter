package com.example.notificationservice.application.dto.request;

public record SendNotificationRequest(String orderId, Long productId, String message) {
}
