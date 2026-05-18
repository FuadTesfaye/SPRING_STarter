package com.example.inventory.application.ports;

public interface InventoryEventPublisher {
    void publishStockReserved(Long orderId);
    void publishStockFailed(Long orderId, String reason);
}