package com.microservices.payment.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Payment {
    private String id;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String reason;
    private LocalDateTime processedAt;
    
    private Payment() {}
    
    public static Payment processSuccessfully(String orderId, BigDecimal amount) {
        Payment payment = new Payment();
        payment.id = UUID.randomUUID().toString();
        payment.orderId = orderId;
        payment.amount = amount;
        payment.status = PaymentStatus.COMPLETED;
        payment.processedAt = LocalDateTime.now();
        return payment;
    }
    
    public static Payment processFailed(String orderId, BigDecimal amount, String reason) {
        Payment payment = new Payment();
        payment.id = UUID.randomUUID().toString();
        payment.orderId = orderId;
        payment.amount = amount;
        payment.status = PaymentStatus.FAILED;
        payment.reason = reason;
        payment.processedAt = LocalDateTime.now();
        return payment;
    }
    
    public static Payment reconstruct(String id, String orderId, BigDecimal amount,
                                       PaymentStatus status, String reason, LocalDateTime processedAt) {
        Payment payment = new Payment();
        payment.id = id;
        payment.orderId = orderId;
        payment.amount = amount;
        payment.status = status;
        payment.reason = reason;
        payment.processedAt = processedAt;
        return payment;
    }
    
    public String getId() { return id; }
    public String getOrderId() { return orderId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getReason() { return reason; }
    public LocalDateTime getProcessedAt() { return processedAt; }
}
