package com.example.order.presentation.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotNull(message = "User ID is required")
        Long userId,
        @NotNull(message = "Product ID is required")
        Long productId,
        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity,
        @NotNull(message = "Total amount is required")
        @DecimalMin(value = "0.01", message = "Total amount must be greater than zero")
        BigDecimal totalAmount
) {
}
