package com.example.inventory.application.port.out;

import com.example.inventory.domain.model.Stock;

import java.util.Optional;

public interface StockRepository {

    Stock save(Stock stock);

    Optional<Stock> findByProductId(Long productId);

    Optional<Stock> findById(Long id);
}
