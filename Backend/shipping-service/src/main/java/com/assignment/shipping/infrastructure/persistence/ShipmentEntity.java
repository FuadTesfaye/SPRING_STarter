package com.assignment.shipping.infrastructure.persistence;

import com.assignment.shipping.domain.model.Shipment;
import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {

    @Id
    @Column(columnDefinition = "VARCHAR(36)")
    public String id;

    @Column(nullable = false)
    public String orderId;

    @Column(nullable = false)
    public String userId;

    @Column(nullable = false)
    public String productName;

    @Column(nullable = false)
    public int quantity;

    @Column(nullable = false)
    public String status;

    @Column(nullable = false)
    public String trackingNumber;

    public Instant createdAt;

    public static ShipmentEntity fromDomain(Shipment s) {
        ShipmentEntity e = new ShipmentEntity();
        e.id = s.getId().toString();
        e.orderId = s.getOrderId().toString();
        e.userId = s.getUserId().toString();
        e.productName = s.getProductName();
        e.quantity = s.getQuantity();
        e.status = s.getStatus();
        e.trackingNumber = s.getTrackingNumber();
        e.createdAt = s.getCreatedAt();
        return e;
    }

    public Shipment toDomain() {
        return new Shipment(UUID.fromString(id), UUID.fromString(orderId), UUID.fromString(userId),
            productName, quantity, status, trackingNumber, createdAt);
    }
}
