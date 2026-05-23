package com.ecom.inventory.domain.repository;

import com.ecom.inventory.domain.model.Stock;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository {
    Optional<Stock> findByProductId(UUID productId);
    List<Stock> findAll();
    Stock save(Stock stock);
}
