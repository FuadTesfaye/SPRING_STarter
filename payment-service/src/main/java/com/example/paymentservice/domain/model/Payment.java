package com.example.paymentservice.domain.model;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
public class Payment {
    public enum Status { COMPLETED, FAILED }
    private UUID id; private UUID orderId; private BigDecimal amount;
    private Status status; private Instant processedAt;
    public Payment() {}
    public Payment(UUID id, UUID orderId, BigDecimal amount, Status s, Instant t) {
        this.id=id; this.orderId=orderId; this.amount=amount; this.status=s; this.processedAt=t;
    }
    public UUID getId() { return id; } public void setId(UUID v) { this.id=v; }
    public UUID getOrderId() { return orderId; } public void setOrderId(UUID v) { this.orderId=v; }
    public BigDecimal getAmount() { return amount; } public void setAmount(BigDecimal v) { this.amount=v; }
    public Status getStatus() { return status; } public void setStatus(Status v) { this.status=v; }
    public Instant getProcessedAt() { return processedAt; } public void setProcessedAt(Instant v) { this.processedAt=v; }
}
