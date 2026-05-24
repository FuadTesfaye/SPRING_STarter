package com.ecom.inventory.infrastructure.messaging;

import com.ecom.inventory.application.dto.OrderCreatedEvent;
import com.ecom.inventory.application.service.InventoryManager;

public class OrderCreatedListener {
    private final InventoryManager inventoryManager;

    public OrderCreatedListener(InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    public void handleOrderCreated(OrderCreatedEvent event) {
        inventoryManager.handleOrderCreated(event);
    }
}
