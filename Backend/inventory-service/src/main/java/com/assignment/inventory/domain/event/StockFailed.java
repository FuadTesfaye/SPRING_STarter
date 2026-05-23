package com.assignment.inventory.domain.event;

import java.util.UUID;

public record StockFailed(UUID orderId, UUID userId, String productName, String reason) {
    public static StockFailed of(UUID orderId, UUID userId, String productName, String reason) {
        return new StockFailed(orderId, userId, productName, reason);
    }
}
