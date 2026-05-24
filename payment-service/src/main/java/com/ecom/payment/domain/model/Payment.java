package com.ecom.payment.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private final UUID id;
    private final UUID orderId;
    private final BigDecimal amount;
    private final String status;
    private final LocalDateTime processedAt;

    public Payment(UUID id, UUID orderId, BigDecimal amount, String status, LocalDateTime processedAt) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.processedAt = processedAt;
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public String getStatus() { return status; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}
