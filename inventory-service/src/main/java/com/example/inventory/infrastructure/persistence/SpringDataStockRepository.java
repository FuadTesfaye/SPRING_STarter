package com.example.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataStockRepository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findByProductId(Long productId);
}
