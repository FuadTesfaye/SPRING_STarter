package com.example.inventory.application.dto;

import com.example.inventory.domain.model.ReservationStatus;

import java.time.Instant;

public record ReservationResponse(
        Long id,
        Long orderId,
        Long productId,
        int quantity,
        ReservationStatus status,
        Instant createdAt
) {
}
