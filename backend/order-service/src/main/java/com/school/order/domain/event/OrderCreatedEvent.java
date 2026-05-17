package com.school.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderCreatedEvent {

    private Long orderId;
    private String studentId;
    private String studentName;
    private String feeType;
    private BigDecimal amount;
    private LocalDateTime occurredAt;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(Long orderId, String studentId, String studentName,
                             String feeType, BigDecimal amount) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.feeType = feeType;
        this.amount = amount;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
