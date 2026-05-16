package com.example.shipping.infrastructure.persistence;

import com.example.shipping.domain.model.ShipmentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String shipmentReference;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private Instant createdAt;
    private Instant updatedAt;

    public ShipmentEntity() {
    }

    public ShipmentEntity(Long orderId, String shipmentReference, ShipmentStatus status, Instant createdAt, Instant updatedAt) {
        this.orderId = orderId;
        this.shipmentReference = shipmentReference;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getShipmentReference() {
        return shipmentReference;
    }

    public void setShipmentReference(String shipmentReference) {
        this.shipmentReference = shipmentReference;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public void setStatus(ShipmentStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
