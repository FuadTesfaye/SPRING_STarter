package com.assignment.payment.domain.event;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompleted(UUID paymentId, UUID orderId, UUID userId, BigDecimal amount) {
    public static PaymentCompleted of(UUID paymentId, UUID orderId, UUID userId, BigDecimal amount) {
        return new PaymentCompleted(paymentId, orderId, userId, amount);
    }
}
