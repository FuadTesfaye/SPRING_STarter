package com.school.shipping.domain.event;

import java.time.LocalDateTime;

public class ShipmentCreatedEvent {

    private Long orderId;
    private Long shipmentId;
    private String studentId;
    private String trackingNumber;
    private LocalDateTime occurredAt;

    public ShipmentCreatedEvent() {}

    public ShipmentCreatedEvent(Long orderId, Long shipmentId, String studentId, String trackingNumber) {
        this.orderId = orderId;
        this.shipmentId = shipmentId;
        this.studentId = studentId;
        this.trackingNumber = trackingNumber;
        this.occurredAt = LocalDateTime.now();
    }

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public Long getShipmentId() { return shipmentId; }
    public void setShipmentId(Long shipmentId) { this.shipmentId = shipmentId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTrackingNumber() { return trackingNumber; }
    public void setTrackingNumber(String trackingNumber) { this.trackingNumber = trackingNumber; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}
