package com.microservices.shipping.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Shipment {
    private String id;
    private String orderId;
    private String paymentId;
    private String reservationId;
    private String trackingNumber;
    private ShipmentStatus status;
    private LocalDateTime createdAt;
    
    private Shipment() {}
    
    /**
     * Create a shipment (called when both conditions are met)
     */
    public static Shipment create(String orderId, String paymentId, String reservationId) {
        Shipment shipment = new Shipment();
        shipment.id = UUID.randomUUID().toString();
        shipment.orderId = orderId;
        shipment.paymentId = paymentId;
        shipment.reservationId = reservationId;
        shipment.trackingNumber = generateTrackingNumber();
        shipment.status = ShipmentStatus.SHIPPED;
        shipment.createdAt = LocalDateTime.now();
        return shipment;
    }
    
    private static String generateTrackingNumber() {
        return "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getReservationId() { return reservationId; }
    public void setReservationId(String reservationId) { this.reservationId = reservationId; }
    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }
    public ShipmentStatus getStatus() { return status; }
    public void setStatus(ShipmentStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
