package com.assignment.payment.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private String status;
    private Instant createdAt;

    public Payment() {}

    public Payment(UUID id, UUID orderId, UUID userId, BigDecimal amount, String status, Instant createdAt) {
        this.id = id; this.orderId = orderId; this.userId = userId;
        this.amount = amount; this.status = status; this.createdAt = createdAt;
    }

    public static Payment create(UUID orderId, UUID userId, BigDecimal amount) {
        return new Payment(UUID.randomUUID(), orderId, userId, amount, "PROCESSING", Instant.now());
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public void setStatus(String status) { this.status = status; }
}
