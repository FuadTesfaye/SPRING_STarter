package com.school.payment.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentFailedEvent {

    private Long orderId;
    private String studentId;
    private BigDecimal amount;
    private String reason;
    private LocalDateTime occurredAt;

    public PaymentFailedEvent() {}

    public PaymentFailedEvent(Long orderId, String studentId, BigDecimal amount, String reason) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.amount = amount;
        this.reason = reason;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
