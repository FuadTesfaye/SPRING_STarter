package com.assignment.inventory.domain.repository;


import com.assignment.inventory.domain.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Long> {
    Optional<InventoryItem> findByProductName(String productName);
}
