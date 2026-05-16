package com.example.events;

import java.time.Instant;

public record OrderCreatedEvent(
        Long orderId,
        Long userId,
        Long productId,
        int quantity,
        Instant occurredAt
) {
}
