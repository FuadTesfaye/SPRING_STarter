package com.example.payment.application.dto;

/**
 * Application-layer command derived from inbound integration messages (outer ring adapts MQ DTO → this).
 */
public record OrderPaymentCommand(String orderId, String productId, int quantity, double totalAmount) {
}
