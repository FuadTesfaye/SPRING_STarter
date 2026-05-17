package com.app.shipping.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "shipments")
public class ShipmentEntity {
    @Id
    private UUID id;
    private UUID orderId;
    private String trackingNumber;
    private boolean paymentReceived;
    private boolean stockReserved;

    public ShipmentEntity() {}

    public ShipmentEntity(UUID id, UUID orderId, String trackingNumber, boolean paymentReceived, boolean stockReserved) {
        this.id = id;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.paymentReceived = paymentReceived;
        this.stockReserved = stockReserved;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public boolean isPaymentReceived() { return paymentReceived; }
    public void setPaymentReceived(boolean paymentReceived) { this.paymentReceived = paymentReceived; }
    public boolean isStockReserved() { return stockReserved; }
    public void setStockReserved(boolean stockReserved) { this.stockReserved = stockReserved; }
}
