package com.example.shipping.application.dto;

import com.example.shipping.domain.model.ShipmentStatus;

import java.time.Instant;

public record ShipmentResponse(
        Long id,
        Long orderId,
        String shipmentReference,
        ShipmentStatus status,
        Instant createdAt
) {
}
