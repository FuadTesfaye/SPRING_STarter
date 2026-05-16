package com.example.inventory.infrastructure.persistence;

import com.example.inventory.application.port.out.StockRepository;
import com.example.inventory.domain.model.Stock;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockPersistenceAdapter implements StockRepository {

    private final SpringDataStockRepository springDataStockRepository;

    public StockPersistenceAdapter(SpringDataStockRepository springDataStockRepository) {
        this.springDataStockRepository = springDataStockRepository;
    }

    @Override
    public Stock save(Stock stock) {
        StockEntity entity = new StockEntity(
                stock.getProductId(),
                stock.getQuantity(),
                stock.getReserved(),
                stock.getCreatedAt(),
                stock.getUpdatedAt()
        );
        if (stock.getId() != null) {
            entity.setId(stock.getId());
        }
        StockEntity saved = springDataStockRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Stock> findByProductId(Long productId) {
        return springDataStockRepository.findByProductId(productId)
                .map(this::toDomain);
    }

    @Override
    public Optional<Stock> findById(Long id) {
        return springDataStockRepository.findById(id)
                .map(this::toDomain);
    }

    private Stock toDomain(StockEntity entity) {
        return new Stock(
                entity.getId(),
                entity.getProductId(),
                entity.getQuantity(),
                entity.getReserved(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
