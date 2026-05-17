package com.app.inventory.application.ports;

import java.util.UUID;

public interface InventoryEventPublisher {
    void publishStockReserved(UUID orderId);
    void publishStockFailed(UUID orderId, String reason);
}
