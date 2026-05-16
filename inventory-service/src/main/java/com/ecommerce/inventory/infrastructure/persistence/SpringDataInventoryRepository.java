package com.ecommerce.inventory.infrastructure.persistence;

import com.ecommerce.inventory.domain.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SpringDataInventoryRepository extends JpaRepository<Inventory, String> {
    Optional<Inventory> findByProductId(String productId);
}
