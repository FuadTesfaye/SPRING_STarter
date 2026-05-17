package com.microservices.inventory.domain.event;

public interface InventoryEventPublisher {
    void publishStockReserved(StockReservedEvent event);
    void publishStockFailed(StockFailedEvent event);
}
