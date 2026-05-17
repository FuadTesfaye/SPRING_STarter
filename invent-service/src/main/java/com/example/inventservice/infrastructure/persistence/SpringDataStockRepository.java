package com.example.inventservice.infrastructure.persistence;

import com.example.inventservice.infrastructure.persistence.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataStockRepository extends JpaRepository<StockEntity, UUID> {
}