package com.app.shipping.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(UUID paymentId, UUID orderId, BigDecimal amount) {}
