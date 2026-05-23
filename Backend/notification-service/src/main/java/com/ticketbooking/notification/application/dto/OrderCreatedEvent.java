package com.ticketbooking.notification.application.dto;
import java.math.BigDecimal;
public record OrderCreatedEvent(String orderId, String userId, String productName, int quantity, BigDecimal price,
                                String userEmail, String fullName) {}
