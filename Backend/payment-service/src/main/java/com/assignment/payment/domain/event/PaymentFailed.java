package com.assignment.payment.domain.event;

import java.util.UUID;

public record PaymentFailed(UUID paymentId, UUID orderId, UUID userId, String reason) {
    public static PaymentFailed of(UUID paymentId, UUID orderId, UUID userId, String reason) {
        return new PaymentFailed(paymentId, orderId, userId, reason);
    }
}
