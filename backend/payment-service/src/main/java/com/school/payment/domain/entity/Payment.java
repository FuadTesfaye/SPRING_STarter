package com.school.payment.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private Long id;
    private Long orderId;
    private String studentId;
    private BigDecimal amount;
    private String status;   // SUCCESS, FAILED
    private String reference;
    private LocalDateTime processedAt;

    public Payment() {}

    public Payment(Long orderId, String studentId, BigDecimal amount) {
        this.orderId = orderId;
        this.studentId = studentId;
        this.amount = amount;
        this.processedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
