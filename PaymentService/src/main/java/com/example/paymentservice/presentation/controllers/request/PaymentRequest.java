package com.example.paymentservice.presentation.controllers.request;

public record PaymentRequest(String orderId, Long productId, Integer quantity, double amount) {
}
