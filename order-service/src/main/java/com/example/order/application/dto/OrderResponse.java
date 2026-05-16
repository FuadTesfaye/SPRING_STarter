package com.example.order.application.dto;

import com.example.order.domain.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderResponse(
        Long id,
        Long userId,
        Long productId,
        int quantity,
        BigDecimal totalAmount,
        OrderStatus status,
        Instant createdAt
) {
}
