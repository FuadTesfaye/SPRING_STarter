package com.microservices.notification.infrastructure.messaging;

import java.math.BigDecimal;

public class PaymentFailedEvent {
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private String reason;
    
    public PaymentFailedEvent() {}
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
