package com.eventdriven.paymentservice.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PaymentFailedEvent implements Serializable {
    private Long orderId;
    private String reason;
    private LocalDateTime timestamp;

    public PaymentFailedEvent() {}

    public PaymentFailedEvent(Long orderId, String reason, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.reason = reason;
        this.timestamp = timestamp;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}