package com.example.inventoryservice.application.service;
import com.example.inventoryservice.application.dto.OrderCreatedMessage;
import com.example.inventoryservice.application.port.*;
import com.example.inventoryservice.domain.event.StockFailedEvent;
import com.example.inventoryservice.domain.event.StockReservedEvent;
import com.example.inventoryservice.domain.model.StockItem;
import org.slf4j.*;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class ReserveStockUseCase {
    private static final Logger log = LoggerFactory.getLogger(ReserveStockUseCase.class);
    private final StockRepositoryPort repo;
    private final EventPublisherPort publisher;
    public ReserveStockUseCase(StockRepositoryPort r, EventPublisherPort p) { repo=r; publisher=p; }

    public void handle(OrderCreatedMessage m) {
        StockItem item = repo.findBySku(m.productSku)
            .orElseGet(() -> repo.save(new StockItem(m.productSku, 100))); // seed
        try {
            item.reserve(m.quantity);
            repo.save(item);
            log.info("Reserved {} x {} for order {}", m.quantity, m.productSku, m.orderId);
            publisher.publish("stock.reserved",
                new StockReservedEvent(m.orderId, m.productSku, m.quantity, Instant.now()));
        } catch (Exception ex) {
            log.warn("Stock failure for order {}: {}", m.orderId, ex.getMessage());
            publisher.publish("stock.failed",
                new StockFailedEvent(m.orderId, m.productSku, ex.getMessage(), Instant.now()));
        }
    }
}
