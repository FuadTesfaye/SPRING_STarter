package com.microservices.shipping.domain.model;

public enum ShipmentStatus {
    PENDING,      // Waiting for payment & stock
    READY,        // Both received, ready to ship
    SHIPPED,      // Shipment created
    CANCELLED     // Payment or stock failed
}
