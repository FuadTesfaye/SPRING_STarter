package com.example.inventoryservice.infrastructure.persistence.adapter;

import com.example.inventoryservice.domain.entities.InventoryItem;
import com.example.inventoryservice.domain.interfaces.InventoryRepository;
import com.example.inventoryservice.infrastructure.persistence.repository.SpringDataInventoryRepository;
import java.util.Optional;

public class InventoryPersistenceAdapter implements InventoryRepository {

    private final SpringDataInventoryRepository springDataInventoryRepository;
    private final InventoryPersistenceMapper inventoryPersistenceMapper;

    public InventoryPersistenceAdapter(
            SpringDataInventoryRepository springDataInventoryRepository,
            InventoryPersistenceMapper inventoryPersistenceMapper
    ) {
        this.springDataInventoryRepository = springDataInventoryRepository;
        this.inventoryPersistenceMapper = inventoryPersistenceMapper;
    }

    @Override
    public Optional<InventoryItem> findByProductId(Long productId) {
        return springDataInventoryRepository.findById(productId).map(inventoryPersistenceMapper::toDomain);
    }

    @Override
    public InventoryItem save(InventoryItem inventoryItem) {
        return inventoryPersistenceMapper.toDomain(
                springDataInventoryRepository.save(inventoryPersistenceMapper.toEntity(inventoryItem))
        );
    }
}
