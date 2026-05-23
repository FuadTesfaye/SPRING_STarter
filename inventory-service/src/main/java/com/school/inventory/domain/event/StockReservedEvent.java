package com.school.inventory.domain.event;

import java.time.LocalDateTime;

public class StockReservedEvent {

    private Long orderId;
    private String studentId;
    private String feeType;
    private LocalDateTime occurredAt;

    public StockReservedEvent() {}

    public StockReservedEvent(Long orderId, String studentId, String feeType) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.feeType = feeType;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
