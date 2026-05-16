package com.example.events;

import java.time.Instant;

public record StockReservedEvent(
        Long orderId,
        Long productId,
        int quantity,
        Instant occurredAt
) {
}
