package com.school.inventory.domain.event;

import java.time.LocalDateTime;

public class StockFailedEvent {

    private Long orderId;
    private String studentId;
    private String feeType;
    private String reason;
    private LocalDateTime occurredAt;

    public StockFailedEvent() {}

    public StockFailedEvent(Long orderId, String studentId, String feeType, String reason) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.feeType = feeType;
        this.reason = reason;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
