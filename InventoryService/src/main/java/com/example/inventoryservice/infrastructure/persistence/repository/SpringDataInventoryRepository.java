package com.example.inventoryservice.infrastructure.persistence.repository;

import com.example.inventoryservice.infrastructure.persistence.entity.InventoryItemJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataInventoryRepository extends JpaRepository<InventoryItemJpaEntity, Long> {
}
