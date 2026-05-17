package com.school.payment.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCompletedEvent {

    private Long orderId;
    private Long paymentId;
    private String studentId;
    private BigDecimal amount;
    private String reference;
    private LocalDateTime occurredAt;

    public PaymentCompletedEvent() {}

    public PaymentCompletedEvent(Long orderId, Long paymentId, String studentId,
                                  BigDecimal amount, String reference) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.studentId = studentId;
        this.amount = amount;
        this.reference = reference;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
