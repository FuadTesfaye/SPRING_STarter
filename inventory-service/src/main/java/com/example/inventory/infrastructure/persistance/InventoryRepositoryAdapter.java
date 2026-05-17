package com.example.inventory.infrastructure.persistance;

import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.port.InventoryRepositoryPort;
import org.springframework.stereotype.Component;

@Component
public class InventoryRepositoryAdapter implements InventoryRepositoryPort {

    private final InventoryJpaRepository repository;

    public InventoryRepositoryAdapter(InventoryJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = new InventoryEntity();
        entity.setUsername(inventory.getUsername());
        entity.setStatus(inventory.getStatus());
        repository.save(entity);
        return inventory;
    }
}
