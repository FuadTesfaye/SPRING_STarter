package com.example.shipping.infrastructure.persistence;

import com.example.shipping.domain.model.Shipment;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private String status;
    private String trackingNumber;
    private LocalDateTime createdAt;

    public ShipmentEntity() {}

    public static ShipmentEntity fromDomain(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity();
        entity.id = shipment.getId();
        entity.orderId = shipment.getOrderId();
        entity.userId = shipment.getUserId();
        entity.status = shipment.getStatus();
        entity.trackingNumber = shipment.getTrackingNumber();
        entity.createdAt = shipment.getCreatedAt();
        return entity;
    }

    public Shipment toDomain() {
        return new Shipment(orderId, userId);
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}