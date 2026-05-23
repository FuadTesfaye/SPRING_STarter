package com.assignment.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaInventoryRepository extends JpaRepository<InventoryEntity, String> {
    Optional<InventoryEntity> findByProductName(String productName);
}
