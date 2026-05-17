package com.example.orderservice.application.dto.response;

public record OrderResponse(
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
