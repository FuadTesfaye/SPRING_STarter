package com.ticketbooking.notification.application.dto;
public record PaymentFailedEvent(String paymentId, String orderId, String userId, String reason) {}
