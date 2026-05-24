package com.ecom.order.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(
    UUID productId,
    String productName,
    int quantity,
    BigDecimal price
) {
    public OrderItem {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        if (price.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Price cannot be negative");
    }
}
