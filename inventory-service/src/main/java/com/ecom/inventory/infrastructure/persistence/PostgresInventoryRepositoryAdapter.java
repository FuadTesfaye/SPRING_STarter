package com.ecom.inventory.infrastructure.persistence;

import com.ecom.inventory.domain.model.Stock;
import com.ecom.inventory.domain.repository.InventoryRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class PostgresInventoryRepositoryAdapter implements InventoryRepository {
    private final JpaStockRepository repository;

    public PostgresInventoryRepositoryAdapter(JpaStockRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Stock> findByProductId(UUID productId) {
        return repository.findById(productId)
                .map(entity -> new Stock(entity.getProductId(), entity.getProductName(), entity.getQuantity()));
    }

    @Override
    public java.util.List<Stock> findAll() {
        return repository.findAll().stream()
                .map(entity -> new Stock(entity.getProductId(), entity.getProductName(), entity.getQuantity()))
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = new StockEntity(stock.getProductId(), stock.getProductName(), stock.getQuantity());
        StockEntity saved = repository.save(entity);
        return new Stock(saved.getProductId(), saved.getProductName(), saved.getQuantity());
    }
}
