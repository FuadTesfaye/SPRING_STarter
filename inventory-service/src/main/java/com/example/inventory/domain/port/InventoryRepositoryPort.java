package com.example.inventory.domain.port;

import com.example.inventory.domain.model.Inventory;

public interface InventoryRepositoryPort {
    Inventory save(Inventory inventory);
}
