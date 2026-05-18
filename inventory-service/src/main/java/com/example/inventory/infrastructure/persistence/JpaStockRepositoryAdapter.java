package com.example.inventory.infrastructure.persistence;

import com.example.inventory.domain.model.Stock;
import com.example.inventory.domain.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaStockRepositoryAdapter implements StockRepository {

    private final SpringDataStockRepository repository;

    @Override
    public Optional<Stock> findByProductId(String productId) {
        return repository.findByProductId(productId)
                .map(entity -> new Stock(entity.getId(), entity.getProductId(), entity.getQuantity()));
    }

    @Override
    public void save(Stock stock) {
        // Convert Domain model to Database Entity
        StockEntity entity = new StockEntity(
                stock.getId(),
                stock.getProductId(),
                stock.getQuantity()
        );

        repository.save(entity);
    }
}