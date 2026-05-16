package com.example.events;

import java.time.Instant;

public record ShipmentCreatedEvent(
        Long orderId,
        Long shipmentId,
        String shipmentStatus,
        Instant occurredAt
) {
}
