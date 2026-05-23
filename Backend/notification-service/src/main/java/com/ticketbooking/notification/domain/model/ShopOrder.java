package com.ticketbooking.notification.domain.model;

import java.time.Instant;

public record ShopOrder(
    String orderId,
    String productName,
    int quantity,
    double totalAmount,
    String email,
    String fullName,
    String status,   // PENDING | DELIVERED
    Instant placedAt
) {}
