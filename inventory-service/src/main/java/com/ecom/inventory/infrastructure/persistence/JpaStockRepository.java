package com.ecom.inventory.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaStockRepository extends JpaRepository<StockEntity, UUID> {
}
