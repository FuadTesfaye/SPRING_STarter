package com.example.paymentservice.application.dto.request;

public record PaymentProcessRequest(String orderId, Long productId, Integer quantity, double amount) {
}
