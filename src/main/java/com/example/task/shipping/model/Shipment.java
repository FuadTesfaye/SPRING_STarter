package com.example.task.shipping.model;

public class Shipment {
    private String shipmentId;
    private String orderId;
    private String trackingNumber;
    private String status;

    public Shipment(String shipmentId, String orderId, String trackingNumber) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
        this.status = "PREPARING";
    }

    public String getShipmentId() { return shipmentId; }
    public String getOrderId() { return orderId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getStatus() { return status; }

    public void ship() { this.status = "SHIPPED"; }
    public void deliver() { this.status = "DELIVERED"; }
}
