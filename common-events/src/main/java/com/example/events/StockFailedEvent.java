package com.example.events;

import java.time.Instant;

public record StockFailedEvent(
        Long orderId,
        Long productId,
        int requestedQuantity,
        String reason,
        Instant occurredAt
) {
}
