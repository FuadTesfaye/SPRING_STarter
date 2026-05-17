package com.example.inventservice.application.service;

import com.example.inventservice.application.dto.OrderCreatedEvent;
import com.example.inventservice.domain.model.Stock;
import com.example.inventservice.domain.repository.StockRepository;
import com.example.inventservice.infrastructure.messaging.InventoryEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final StockRepository stockRepository;
    private final InventoryEventPublisher eventPublisher;

    public InventoryService(StockRepository stockRepository, InventoryEventPublisher eventPublisher) {
        this.stockRepository = stockRepository;
        this.eventPublisher = eventPublisher;
    }

    public void checkStock(OrderCreatedEvent event) {
        boolean hasStock = event.getQuantity() <= 10;

        if (hasStock) {
            Stock stock = new Stock(event.getProductName(), 50);
            stock.setReservedQuantity(event.getQuantity());
            stockRepository.save(stock);

            eventPublisher.publishStockReserved(event);
            System.out.println("✅ Stock Reserved for Order: " + event.getOrderId());
        } else {
            eventPublisher.publishStockFailed(event);
            System.out.println("❌ Stock Failed for Order: " + event.getOrderId());
        }
    }
}