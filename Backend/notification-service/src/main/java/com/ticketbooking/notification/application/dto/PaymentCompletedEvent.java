package com.ticketbooking.notification.application.dto;
import java.math.BigDecimal;
public record PaymentCompletedEvent(String paymentId, String orderId, String userId, BigDecimal amount) {}
