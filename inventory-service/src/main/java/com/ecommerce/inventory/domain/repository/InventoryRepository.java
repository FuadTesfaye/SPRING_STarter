package com.ecommerce.inventory.domain.repository;

import com.ecommerce.inventory.domain.model.Inventory;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository {
    Inventory save(Inventory inventory);
    Optional<Inventory> findByProductId(String productId);
    List<Inventory> findAll();
}
