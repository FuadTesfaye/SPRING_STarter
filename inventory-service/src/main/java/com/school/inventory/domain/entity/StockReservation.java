package com.school.inventory.domain.entity;

import java.time.LocalDateTime;

public class StockReservation {

    private Long id;
    private Long orderId;
    private String studentId;
    private String feeType;
    private String status;   // RESERVED, FAILED
    private LocalDateTime reservedAt;

    public StockReservation() {}

    public StockReservation(Long orderId, String studentId, String feeType) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.feeType = feeType;
        this.reservedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getReservedAt() { return reservedAt; }
    public void setReservedAt(LocalDateTime reservedAt) { this.reservedAt = reservedAt; }
}
