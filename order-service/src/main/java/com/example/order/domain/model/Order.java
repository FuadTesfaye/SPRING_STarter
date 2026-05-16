package com.example.order.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Order {

    private final Long id;
    private final Long userId;
    private final Long productId;
    private final int quantity;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final Instant createdAt;

    public Order(
            Long id,
            Long userId,
            Long productId,
            int quantity,
            BigDecimal totalAmount,
            OrderStatus status,
            Instant createdAt
    ) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
