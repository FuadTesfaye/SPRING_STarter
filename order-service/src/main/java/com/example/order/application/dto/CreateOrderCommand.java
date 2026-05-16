package com.example.order.application.dto;

import java.math.BigDecimal;

public record CreateOrderCommand(
        Long userId,
        Long productId,
        int quantity,
        BigDecimal totalAmount
) {
}
