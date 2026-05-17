package com.example.payment.infrastructure.persistence;

import com.example.payment.domain.model.Payment;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "payments")
public class PaymentEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime createdAt;

    public PaymentEntity() {}

    public static PaymentEntity fromDomain(Payment payment) {
        PaymentEntity entity = new PaymentEntity();
        entity.id = payment.getId();
        entity.orderId = payment.getOrderId();
        entity.userId = payment.getUserId();
        entity.amount = payment.getAmount();
        entity.status = payment.getStatus();
        entity.transactionId = payment.getTransactionId();
        entity.createdAt = payment.getCreatedAt();
        return entity;
    }

    public Payment toDomain() {
        Payment payment = new Payment(orderId, userId, amount);
        return payment;
    }

    // Getters and setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}