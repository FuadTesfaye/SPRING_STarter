package com.app.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(UUID id, String customerId, BigDecimal amount, String status) {}
