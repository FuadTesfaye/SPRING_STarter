package com.example.paymentservice.application.dto.response;

public record PaymentProcessResponse(String paymentId, String orderId, String status, String message) {
}
