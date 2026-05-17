package com.microservices.shipping.domain.event;

import java.time.LocalDateTime;

public class ShipmentCreatedEvent {
    private String shipmentId;
    private String orderId;
    private String trackingNumber;
    private LocalDateTime shippedAt;
    
    public ShipmentCreatedEvent() {}
    
    public ShipmentCreatedEvent(String shipmentId, String orderId, String trackingNumber) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.shippedAt = LocalDateTime.now();
    }
    
    public String getShipmentId() { return shipmentId; }
    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public LocalDateTime getShippedAt() { return shippedAt; }
    public void setShippedAt(LocalDateTime shippedAt) { this.shippedAt = shippedAt; }
}
