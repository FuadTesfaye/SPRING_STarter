package com.app.payment.domain;

import java.util.UUID;

public record PaymentFailed(UUID orderId, String reason) {}
