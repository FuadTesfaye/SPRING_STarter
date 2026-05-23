package com.assignment.inventory.domain.event;

import java.util.UUID;

public record StockReserved(UUID orderId, UUID userId, String productName, int quantity) {
    public static StockReserved of(UUID orderId, UUID userId, String productName, int quantity) {
        return new StockReserved(orderId, userId, productName, quantity);
    }
}
