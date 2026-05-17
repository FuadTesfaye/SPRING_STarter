package com.school.shipping.domain.entity;

import java.time.LocalDateTime;

public class Shipment {

    private Long id;
    private Long orderId;
    private String studentId;
    private String trackingNumber;
    private String status;
    private LocalDateTime createdAt;

    public Shipment() {}

    public Shipment(Long orderId, String studentId) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.status = "PROCESSING";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
