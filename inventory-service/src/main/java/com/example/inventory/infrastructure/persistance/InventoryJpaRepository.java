package com.example.inventory.infrastructure.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryJpaRepository extends JpaRepository<InventoryEntity, Long> {
}
