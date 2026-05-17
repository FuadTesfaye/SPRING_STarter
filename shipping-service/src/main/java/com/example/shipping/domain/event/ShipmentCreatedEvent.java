package com.example.shipping.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;

public class ShipmentCreatedEvent {
    private UUID orderId;
    private UUID userId;
    private UUID shipmentId;
    private String trackingNumber;
    private LocalDateTime timestamp;

    public ShipmentCreatedEvent(UUID orderId, UUID userId, UUID shipmentId, String trackingNumber) {
        this.orderId = orderId;
        this.userId = userId;
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
        this.timestamp = LocalDateTime.now();
    }

    public UUID getOrderId() { return orderId; }
    public UUID getUserId() { return userId; }
    public UUID getShipmentId() { return shipmentId; }
    public String getTrackingNumber() { return trackingNumber; }
    public LocalDateTime getTimestamp() { return timestamp; }
}