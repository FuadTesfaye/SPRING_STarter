package com.assignment.inventory.application.usecase;

import com.assignment.inventory.application.dto.OrderCreatedEvent;
import com.assignment.inventory.domain.event.StockFailed;
import com.assignment.inventory.domain.event.StockReserved;
import com.assignment.inventory.domain.model.Inventory;
import com.assignment.inventory.domain.repository.InventoryRepository;
import com.assignment.inventory.infrastructure.messaging.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ReserveStockUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReserveStockUseCase.class);

    private final InventoryRepository repository;
    private final EventPublisher publisher;
    private final ObjectMapper mapper;

    public ReserveStockUseCase(InventoryRepository repository, EventPublisher publisher, ObjectMapper mapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.mapper = mapper;
    }

    public void execute(Map<String, Object> raw) {
        OrderCreatedEvent event = mapper.convertValue(raw, OrderCreatedEvent.class);

        UUID orderId = UUID.fromString(event.orderId());
        UUID userId  = UUID.fromString(event.userId());

        log.info("[INVENTORY] Checking stock: product='{}' quantity={} for order={}",
            event.productName(), event.quantity(), orderId);

        Inventory inventory = repository.findByProductName(event.productName())
            .orElseGet(() -> repository.save(Inventory.create(event.productName(), 100)));

        if (inventory.getAvailableStock() >= event.quantity()) {
            inventory.setAvailableStock(inventory.getAvailableStock() - event.quantity());
            repository.save(inventory);
            publisher.publish("stock.reserved",
                StockReserved.of(orderId, userId, event.productName(), event.quantity()));
            log.info("[INVENTORY] RESERVED — {} x '{}' for order={}", event.quantity(), event.productName(), orderId);
        } else {
            publisher.publish("stock.failed",
                StockFailed.of(orderId, userId, event.productName(),
                    "Insufficient stock — available: " + inventory.getAvailableStock()));
            log.warn("[INVENTORY] FAILED — insufficient stock for order={}", orderId);
        }
    }
}
