package com.example.shipping.domain.model;

import java.time.Instant;

public class Shipment {

    private final Long id;
    private final Long orderId;
    private final String shipmentReference;
    private final ShipmentStatus status;
    private final Instant createdAt;
    private final Instant updatedAt;

    public Shipment(
            Long id,
            Long orderId,
            String shipmentReference,
            ShipmentStatus status,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.shipmentReference = shipmentReference;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getShipmentReference() {
        return shipmentReference;
    }

    public ShipmentStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
