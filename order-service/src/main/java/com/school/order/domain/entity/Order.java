package com.school.order.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Pure domain entity - no Spring or JPA annotations
public class Order {

    private Long id;
    private String studentId;
    private String studentName;
    private String feeType;       // e.g. TUITION, EXAM, LIBRARY
    private BigDecimal amount;
    private String status;        // PENDING, PAID, FAILED
    private LocalDateTime createdAt;

    public Order() {}

    public Order(String studentId, String studentName, String feeType, BigDecimal amount) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.feeType = feeType;
        this.amount = amount;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getFeeType() { return feeType; }
    public void setFeeType(String feeType) { this.feeType = feeType; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
