package com.example.shippingservice.domain.model;


import java.util.UUID;

public class Shipment {
    private UUID id;
    private UUID orderId;
    private String userId;
    private String address; // You can make it simple
    private String status; // PENDING, SHIPPED

    public Shipment() {}

    public Shipment(UUID orderId, String userId) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.userId = userId;
        this.status = "PENDING";
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
