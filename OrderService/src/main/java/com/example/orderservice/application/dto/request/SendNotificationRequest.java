package com.example.orderservice.application.dto.request;

public record SendNotificationRequest(String orderId, Long productId, String message) {
}
