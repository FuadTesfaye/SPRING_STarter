package com.example.inventory.application.dto;

public record ReserveStockCommand(
        Long orderId,
        Long productId,
        int quantity
) {
}
