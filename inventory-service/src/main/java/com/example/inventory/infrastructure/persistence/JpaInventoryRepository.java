package com.example.inventory.infrastructure.persistence;

import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaInventoryRepository implements InventoryRepository {
    private final SpringDataInventoryRepository springDataInventoryRepository;

    public JpaInventoryRepository(SpringDataInventoryRepository springDataInventoryRepository) {
        this.springDataInventoryRepository = springDataInventoryRepository;
    }

    @Override
    public Inventory save(Inventory inventory) {
        InventoryEntity entity = InventoryEntity.fromDomain(inventory);
        entity = springDataInventoryRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Inventory> findByProductId(UUID productId) {
        return springDataInventoryRepository.findByProductId(productId)
                .map(InventoryEntity::toDomain);
    }
}