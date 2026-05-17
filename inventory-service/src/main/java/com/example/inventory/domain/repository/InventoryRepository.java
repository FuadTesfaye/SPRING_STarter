package com.example.inventory.domain.repository;

import com.example.inventory.domain.model.Inventory;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository {
    Inventory save(Inventory inventory);
    Optional<Inventory> findByProductId(UUID productId);
}