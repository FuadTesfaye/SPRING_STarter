package com.ecom.payment.application.dto;

import java.util.UUID;

public record PaymentProcessedEvent(
    UUID orderId,
    String status
) {}
