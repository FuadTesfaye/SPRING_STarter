package com.example.inventoryservice.infrastructure.persistence.adapter;

import com.example.inventoryservice.domain.entities.InventoryItem;
import com.example.inventoryservice.infrastructure.persistence.entity.InventoryItemJpaEntity;

public class InventoryPersistenceMapper {

    public InventoryItemJpaEntity toEntity(InventoryItem item) {
        InventoryItemJpaEntity entity = new InventoryItemJpaEntity();
        entity.setProductId(item.productId());
        entity.setAvailableQuantity(item.availableQuantity());
        return entity;
    }

    public InventoryItem toDomain(InventoryItemJpaEntity entity) {
        return new InventoryItem(entity.getProductId(), entity.getAvailableQuantity());
    }
}
