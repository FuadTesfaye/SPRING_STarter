package com.assignment.shipping.domain.event;

import java.util.UUID;

public record ShipmentCreated(UUID shipmentId, UUID orderId, UUID userId, String productName, int quantity, String trackingNumber) {
    public static ShipmentCreated of(UUID shipmentId, UUID orderId, UUID userId, String productName, int quantity, String trackingNumber) {
        return new ShipmentCreated(shipmentId, orderId, userId, productName, quantity, trackingNumber);
    }
}
