package com.app.shipping.domain;

import java.util.UUID;

public class Shipment {
    private UUID id;
    private UUID orderId;
    private String trackingNumber;
    private boolean paymentReceived;
    private boolean stockReserved;

    public Shipment() {}

    public Shipment(UUID id, UUID orderId, String trackingNumber) {
        this.id = id;
        this.orderId = orderId;
        this.trackingNumber = trackingNumber;
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
    
    public boolean isReady() {
        return paymentReceived && stockReserved;
    }
}
