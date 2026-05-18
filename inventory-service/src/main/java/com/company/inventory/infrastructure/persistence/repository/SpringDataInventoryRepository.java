package com.company.inventory.infrastructure.persistence.repository;

import com.company.inventory.infrastructure.persistence.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataInventoryRepository extends JpaRepository<ProductStockEntity, String> {
}
