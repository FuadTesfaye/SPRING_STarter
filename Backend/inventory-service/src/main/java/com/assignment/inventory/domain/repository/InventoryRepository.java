package com.assignment.inventory.domain.repository;

import com.assignment.inventory.domain.model.Inventory;
import java.util.Optional;

public interface InventoryRepository {
    Inventory save(Inventory inventory);
    Optional<Inventory> findByProductName(String productName);
}
