package com.example.payment.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class PaymentEvent {
    private UUID orderId;
    private UUID userId;
    private UUID paymentId;
    private String transactionId;
    private BigDecimal amount;
    private String eventType; // "PaymentCompleted" or "PaymentFailed"
    private LocalDateTime timestamp;

    public PaymentEvent(UUID orderId, UUID userId, UUID paymentId, 
                       String transactionId, BigDecimal amount, String eventType) {
        this.orderId = orderId;
        this.userId = userId;
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public UUID getPaymentId() { return paymentId; }
    public String getTransactionId() { return transactionId; }
    public BigDecimal getAmount() { return amount; }
    public String getEventType() { return eventType; }
    public LocalDateTime getTimestamp() { return timestamp; }
}