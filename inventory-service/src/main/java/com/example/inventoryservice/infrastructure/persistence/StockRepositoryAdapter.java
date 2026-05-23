package com.example.inventoryservice.infrastructure.persistence;
import com.example.inventoryservice.application.port.StockRepositoryPort;
import com.example.inventoryservice.domain.model.StockItem;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public class StockRepositoryAdapter implements StockRepositoryPort {
    private final StockJpaRepository jpa;
    public StockRepositoryAdapter(StockJpaRepository j) { jpa=j; }
    @Override public Optional<StockItem> findBySku(String sku) {
        return jpa.findById(sku).map(e -> new StockItem(e.getSku(), e.getAvailable()));
    }
    @Override public StockItem save(StockItem item) {
        StockEntity e = new StockEntity();
        e.setSku(item.getSku()); e.setAvailable(item.getAvailable());
        jpa.save(e); return item;
    }
}
