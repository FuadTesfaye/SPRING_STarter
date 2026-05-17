package com.example.inventservice.infrastructure.persistence;

import com.example.inventservice.domain.model.Stock;
import com.example.inventservice.infrastructure.persistence.StockEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class StockRepositoryImpl implements com.example.inventservice.domain.repository.StockRepository {

    private final SpringDataStockRepository jpaRepository;

    public StockRepositoryImpl(SpringDataStockRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = new StockEntity();
        entity.setId(stock.getId());
        entity.setProductName(stock.getProductName());
        entity.setAvailableQuantity(stock.getAvailableQuantity());
        entity.setReservedQuantity(stock.getReservedQuantity());

        StockEntity savedEntity = jpaRepository.save(entity);

        // Convert back to domain model
        Stock savedStock = new Stock();
        savedStock.setId(savedEntity.getId());
        savedStock.setProductName(savedEntity.getProductName());
        savedStock.setAvailableQuantity(savedEntity.getAvailableQuantity());
        savedStock.setReservedQuantity(savedEntity.getReservedQuantity());

        return savedStock;
    }

    @Override
    public Optional<Stock> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> {
                    Stock stock = new Stock();
                    stock.setId(entity.getId());
                    stock.setProductName(entity.getProductName());
                    stock.setAvailableQuantity(entity.getAvailableQuantity());
                    stock.setReservedQuantity(entity.getReservedQuantity());
                    return stock;
                });
    }
}