package com.example.events;

import java.time.Instant;

public record PaymentFailedEvent(
        Long orderId,
        String reason,
        Instant occurredAt
) {
}
