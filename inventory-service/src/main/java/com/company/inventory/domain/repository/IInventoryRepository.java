package com.company.inventory.domain.repository;

import com.company.inventory.domain.model.ProductStock;
import java.util.Optional;

public interface IInventoryRepository {
    Optional<ProductStock> findByProductId(String productId);
    void save(ProductStock stock);
}
