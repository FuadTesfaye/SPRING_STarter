package com.example.orderservice.application.dto;

import java.util.UUID;

public class OrderCreatedEvent {

    private UUID orderId;
    private String productName;
    private int quantity;
    private String status;
    private Long userId;  // Added for payment service
    private Double totalAmount;  // Added for payment service

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, String productName, int quantity, String status) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
    }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    // Add a convenience method for String UUID (for other services)
    public String getOrderIdAsString() {
        return orderId != null ? orderId.toString() : null;
    }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(Double totalAmount) { this.totalAmount = totalAmount; }

    public UUID getId() {
        return orderId;
    }
}