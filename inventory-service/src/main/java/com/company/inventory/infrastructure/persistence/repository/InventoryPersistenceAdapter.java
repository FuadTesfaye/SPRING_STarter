package com.company.inventory.infrastructure.persistence.repository;

import com.company.inventory.domain.model.ProductStock;
import com.company.inventory.domain.repository.IInventoryRepository;
import com.company.inventory.infrastructure.persistence.entity.ProductStockEntity;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class InventoryPersistenceAdapter implements IInventoryRepository {
    private final SpringDataInventoryRepository repository;

    public InventoryPersistenceAdapter(SpringDataInventoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<ProductStock> findByProductId(String productId) {
        return repository.findById(productId).map(ProductStockEntity::toDomain);
    }

    @Override
    public void save(ProductStock stock) {
        repository.save(ProductStockEntity.fromDomain(stock));
    }
}
