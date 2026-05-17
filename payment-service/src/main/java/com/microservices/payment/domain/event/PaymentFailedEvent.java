package com.microservices.payment.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentFailedEvent {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private String reason;
    private LocalDateTime failedAt;
    
    public PaymentFailedEvent() {}
    
    public PaymentFailedEvent(String paymentId, String orderId, BigDecimal amount, String reason) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.reason = reason;
        this.failedAt = LocalDateTime.now();
    }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public LocalDateTime getFailedAt() { return failedAt; }
    public void setFailedAt(LocalDateTime failedAt) { this.failedAt = failedAt; }
}
