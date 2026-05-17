package com.eventdriven.paymentservice.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCompletedEvent implements Serializable {
    private Long orderId;
    private Long paymentId;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(Long orderId, Long paymentId, BigDecimal amount, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}