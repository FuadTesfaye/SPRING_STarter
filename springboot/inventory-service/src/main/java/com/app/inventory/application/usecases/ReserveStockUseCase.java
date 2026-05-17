package com.app.inventory.application.usecases;

import com.app.inventory.application.ports.InventoryEventPublisher;
import com.app.inventory.domain.InventoryRepository;
import java.util.UUID;

public class ReserveStockUseCase {
    private final InventoryRepository inventoryRepository;
    private final InventoryEventPublisher eventPublisher;

    public ReserveStockUseCase(InventoryRepository inventoryRepository, InventoryEventPublisher eventPublisher) {
        this.inventoryRepository = inventoryRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(UUID orderId) {
        // Mock logic: randomly decide if stock is available
        // In a real app, we would use the order details to know what to reserve
        boolean success = inventoryRepository.checkAndReserve("MOCK-SKU", 1);
        
        if (success) {
            eventPublisher.publishStockReserved(orderId);
        } else {
            eventPublisher.publishStockFailed(orderId, "Out of stock");
        }
    }
}
