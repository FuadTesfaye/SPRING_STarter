package com.example.shippingservice.application.dto;

import java.util.UUID;

public class StockReservedEvent {
    private UUID orderId;
    private String userId;
    private String productName;
    private int quantity;

    public StockReservedEvent() {}

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}