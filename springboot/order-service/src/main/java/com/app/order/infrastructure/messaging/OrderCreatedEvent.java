package com.app.order.infrastructure.messaging;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(UUID orderId, String customerId, BigDecimal amount) {}
