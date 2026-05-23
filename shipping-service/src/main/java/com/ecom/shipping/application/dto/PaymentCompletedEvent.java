package com.ecom.shipping.application.dto;

import java.util.UUID;

public record PaymentCompletedEvent(UUID orderId) {}
