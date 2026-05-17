package com.example.paymentservice.domain.model;

import java.util.UUID;

public class Payment {

    private UUID id;
    private UUID orderId;
    private Long userId;
    private double amount;
    private String status; // PENDING, COMPLETED, FAILED

    public Payment() {}

    public Payment(UUID orderId, Long userId, double amount) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.userId = userId;
        this.amount = amount;
        this.status = "PENDING";
    }

    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    // FIXED HERE
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}