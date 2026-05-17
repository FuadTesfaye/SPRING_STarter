package com.example.inventservice.domain.repository;

import com.example.inventservice.domain.model.Stock;
import java.util.Optional;
import java.util.UUID;

public interface StockRepository {
    Stock save(Stock stock);
    Optional<Stock> findById(UUID id);
}