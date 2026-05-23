package com.assignment.shipping.domain.model;

import java.time.Instant;
import java.util.UUID;

public class Shipment {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private String productName;
    private int quantity;
    private String status;
    private String trackingNumber;
    private Instant createdAt;

    public Shipment() {}

    public Shipment(UUID id, UUID orderId, UUID userId, String productName, int quantity, String status, String trackingNumber, Instant createdAt) {
        this.id = id; this.orderId = orderId; this.userId = userId;
        this.productName = productName; this.quantity = quantity;
        this.status = status; this.trackingNumber = trackingNumber; this.createdAt = createdAt;
    }

    public static Shipment create(UUID orderId, UUID userId, String productName, int quantity) {
        return new Shipment(UUID.randomUUID(), orderId, userId, productName, quantity,
            "CREATED", "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(), Instant.now());
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public String getStatus() { return status; }
    public String getTrackingNumber() { return trackingNumber; }
    public Instant getCreatedAt() { return createdAt; }
}
