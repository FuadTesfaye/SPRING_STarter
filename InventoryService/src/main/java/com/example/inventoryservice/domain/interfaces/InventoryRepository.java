package com.example.inventoryservice.domain.interfaces;

import com.example.inventoryservice.domain.entities.InventoryItem;
import java.util.Optional;

public interface InventoryRepository {

    Optional<InventoryItem> findByProductId(Long productId);

    InventoryItem save(InventoryItem inventoryItem);
}
