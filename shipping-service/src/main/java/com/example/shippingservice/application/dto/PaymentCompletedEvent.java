package com.example.shippingservice.application.dto;

import java.util.UUID;

public class PaymentCompletedEvent {
    private UUID orderId;
    private String userId;
    private double amount;

    public PaymentCompletedEvent() {}

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}
