package com.ecom.shipping.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Shipment {
    private final UUID id;
    private final UUID orderId;
    private final String status;
    private final String trackingNumber;
    private final LocalDateTime shippedAt;

    public Shipment(UUID id, UUID orderId, String status, String trackingNumber, LocalDateTime shippedAt) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.shippedAt = shippedAt;
    }

    public UUID getId() { return id; }
    public UUID getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getShippedAt() { return shippedAt; }
}
