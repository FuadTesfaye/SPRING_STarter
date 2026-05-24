package com.ecom.shipping.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private String status;
    private String trackingNumber;
    private LocalDateTime shippedAt;

    public ShipmentEntity() {}

    public ShipmentEntity(UUID id, UUID orderId, String status, String trackingNumber, LocalDateTime shippedAt) {
        this.id = id;
        this.orderId = orderId;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.shippedAt = shippedAt;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public void setShippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; }
}
