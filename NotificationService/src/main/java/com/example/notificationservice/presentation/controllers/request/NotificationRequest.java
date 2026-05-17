package com.example.notificationservice.presentation.controllers.request;

public record NotificationRequest(String orderId, Long productId, String message) {
}
