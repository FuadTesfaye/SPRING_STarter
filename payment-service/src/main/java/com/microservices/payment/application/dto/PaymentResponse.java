package com.microservices.payment.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO: PaymentResponse
 * 
 * WHAT: Clean representation of payment for API responses
 * WHY: Don't expose domain Payment object directly to API
 */
public class PaymentResponse {
    
    private String paymentId;
    private String orderId;
    private BigDecimal amount;
    private String status;
    private String reason;
    private LocalDateTime processedAt;
    
    public PaymentResponse() {}
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
