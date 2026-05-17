package com.example.payment.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime createdAt;

    public Payment(UUID orderId, UUID userId, BigDecimal amount) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = "PENDING";
        this.transactionId = "TXN" + System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getTransactionId() { return transactionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    public void setStatus(String status) { this.status = status; }
}