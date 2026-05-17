package com.example.shipmentservice.domain.entities;

import com.example.shipmentservice.domain.enums.ShipmentStatus;

public class Shipment {

    private final String shipmentId;
    private final String orderId;
    private final Long productId;
    private final int quantity;
    private final ShipmentStatus status;

    public Shipment(String shipmentId, String orderId, Long productId, int quantity, ShipmentStatus status) {
        this.shipmentId = shipmentId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.status = status;
    }

    public String shipmentId() {
        return shipmentId;
    }

    public String orderId() {
        return orderId;
    }

    public Long productId() {
        return productId;
    }

    public int quantity() {
        return quantity;
    }

    public ShipmentStatus status() {
        return status;
    }
}
