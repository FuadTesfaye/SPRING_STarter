package com.example.shipping.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Shipment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private String status;
    private String trackingNumber;
    private LocalDateTime createdAt;

    public Shipment(UUID orderId, UUID userId) {
        this.id = UUID.randomUUID();
        this.orderId = orderId;
        this.userId = userId;
        this.status = "PREPARING";
        this.trackingNumber = "TRK" + System.currentTimeMillis();
        this.createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public String getStatus() { return status; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}