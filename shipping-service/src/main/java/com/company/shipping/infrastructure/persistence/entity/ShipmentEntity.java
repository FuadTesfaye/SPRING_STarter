package com.company.shipping.infrastructure.persistence.entity;

import com.company.shipping.domain.model.Shipment;
import jakarta.persistence.*;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String status;

    public ShipmentEntity() {}

    public static ShipmentEntity fromDomain(Shipment shipment) {
        ShipmentEntity entity = new ShipmentEntity();
        entity.id = shipment.getId();
        entity.orderId = shipment.getOrderId();
        entity.trackingNumber = shipment.getTrackingNumber();
        entity.status = shipment.getStatus();
        return entity;
    }

    public Shipment toDomain() {
        return new Shipment(id, orderId, trackingNumber, status);
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
