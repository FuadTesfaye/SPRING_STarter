package com.example.orderservice.presentation.controllers.response;

public record OrderResponseBody(
        String orderId,
        Long productId,
        Integer quantity,
        String status,
        String inventoryStatus,
        String paymentStatus,
        String shipmentId,
        String shipmentStatus,
        String notificationStatus,
        String message
) {
}
