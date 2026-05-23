package com.assignment.order.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreated(
    UUID orderId,
    UUID userId,
    String productName,
    int quantity,
    BigDecimal price,
    String userEmail,
    String fullName
) {
    public static OrderCreated of(UUID orderId, UUID userId, String productName, int quantity, BigDecimal price,
                                   String userEmail, String fullName) {
        return new OrderCreated(orderId, userId, productName, quantity, price, userEmail, fullName);
    }
}
