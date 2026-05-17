package com.eventdriven.shippingservice.application.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShipmentCreatedEvent implements Serializable {
    private Long orderId;
    private Long shipmentId;
    private String trackingNumber;
    private LocalDateTime timestamp;

    public ShipmentCreatedEvent() {}

    public ShipmentCreatedEvent(Long orderId, Long shipmentId, String trackingNumber, LocalDateTime timestamp) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
        this.trackingNumber = trackingNumber;
        this.timestamp = timestamp;
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public Long getShipmentId() { return shipmentId; }
    public void setShipmentId(Long shipmentId) { this.shipmentId = shipmentId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}