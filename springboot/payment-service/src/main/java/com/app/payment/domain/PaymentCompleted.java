package com.app.payment.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompleted(UUID paymentId, UUID orderId, BigDecimal amount) {}
