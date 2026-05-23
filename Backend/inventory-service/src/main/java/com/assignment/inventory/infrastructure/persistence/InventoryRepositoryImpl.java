package com.assignment.inventory.infrastructure.persistence;

import com.assignment.inventory.domain.model.Inventory;
import com.assignment.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final JpaInventoryRepository jpa;

    public InventoryRepositoryImpl(JpaInventoryRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Inventory save(Inventory inventory) {
        return jpa.save(InventoryEntity.fromDomain(inventory)).toDomain();
    }

    @Override
    public Optional<Inventory> findByProductName(String productName) {
        return jpa.findByProductName(productName).map(InventoryEntity::toDomain);
    }
}
