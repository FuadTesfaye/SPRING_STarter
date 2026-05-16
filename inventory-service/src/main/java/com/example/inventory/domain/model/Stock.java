package com.example.inventory.domain.model;

import java.time.Instant;

public class Stock {

    private final Long id;
    private final Long productId;
    private final int quantity;
    private final int reserved;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Stock(
            Long id,
            Long productId,
            int quantity,
            int reserved,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.reserved = reserved;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getReserved() {
        return reserved;
    }

    public int getAvailable() {
        return quantity - reserved;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public boolean canReserve(int requestedQuantity) {
        return getAvailable() >= requestedQuantity;
    }
}
