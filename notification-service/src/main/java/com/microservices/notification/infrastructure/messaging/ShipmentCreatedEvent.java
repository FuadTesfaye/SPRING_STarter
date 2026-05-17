package com.microservices.notification.infrastructure.messaging;

public class ShipmentCreatedEvent {
    private String shipmentId;
    private String orderId;
    private String trackingNumber;
    
    public ShipmentCreatedEvent() {}
    
    public String getShipmentId() { return shipmentId; }
    public void setShipmentId(String shipmentId) { this.shipmentId = shipmentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
}
