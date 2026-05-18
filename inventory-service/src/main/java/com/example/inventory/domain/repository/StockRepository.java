package com.example.inventory.domain.repository;
import com.example.inventory.domain.model.Stock;
import java.util.Optional;

public interface StockRepository {
    Optional<Stock> findByProductId(String productId);
    void save(Stock stock);
}