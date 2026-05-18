package com.example.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataStockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByProductId(String productId);
}