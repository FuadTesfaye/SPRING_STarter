package com.example.inventoryservice.application.port;
import com.example.inventoryservice.domain.model.StockItem;
import java.util.Optional;
public interface StockRepositoryPort {
    Optional<StockItem> findBySku(String sku);
    StockItem save(StockItem item);
}
