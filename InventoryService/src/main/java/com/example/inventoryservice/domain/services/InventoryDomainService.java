package com.example.inventoryservice.domain.services;

import com.example.inventoryservice.domain.entities.InventoryItem;

public class InventoryDomainService {

    public InventoryItem reserve(InventoryItem inventoryItem, int requestedQuantity) {
        return inventoryItem.withAvailableQuantity(inventoryItem.availableQuantity() - requestedQuantity);
    }
}
