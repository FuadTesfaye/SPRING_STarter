package com.ecom.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
    UUID orderId,
    UUID customerId,
    BigDecimal totalAmount,
    String status
) {}
