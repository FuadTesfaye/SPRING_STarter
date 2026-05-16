package com.example.events;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentCompletedEvent(
        Long orderId,
        String paymentReference,
        BigDecimal amount,
        Instant occurredAt
) {
}
