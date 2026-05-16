package com.example.payment.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class Payment {

    private final Long id;
    private final Long orderId;
    private final BigDecimal amount;
    private final PaymentStatus status;
    private final String reference;
    private final String failureReason;
    private final Instant processedAt;

    public Payment(
            Long id,
            Long orderId,
            BigDecimal amount,
            PaymentStatus status,
            String reference,
            String failureReason,
            Instant processedAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.amount = amount;
        this.status = status;
        this.reference = reference;
        this.failureReason = failureReason;
        this.processedAt = processedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getReference() {
        return reference;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}
