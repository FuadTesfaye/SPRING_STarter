package com.example.inventory.domain.model;

import java.time.Instant;

public class Reservation {

    private final Long id;
    private final Long orderId;
    private final Long productId;
    private final int quantity;
    private final ReservationStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Reservation(
            Long id,
            Long orderId,
            Long productId,
            int quantity,
            ReservationStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
