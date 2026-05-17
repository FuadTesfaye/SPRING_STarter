package com.example.inventory.application.service;

import com.example.inventory.domain.event.OrderCreatedEvent;
import com.example.inventory.domain.event.StockEvent;
import com.example.inventory.domain.model.Inventory;
import com.example.inventory.domain.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);
    private final InventoryRepository inventoryRepository;
    private final RabbitTemplate rabbitTemplate;

    public InventoryService(InventoryRepository inventoryRepository, RabbitTemplate rabbitTemplate) {
        this.inventoryRepository = inventoryRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void checkAndReserveStock(OrderCreatedEvent event) {
        log.info("Checking stock for order: {}", event.getOrderId());
        
        // Initialize some stock if not exists
        for (OrderCreatedEvent.OrderItemDetail item : event.getItems()) {
            if (inventoryRepository.findByProductId(item.getProductId()).isEmpty()) {
                Inventory inventory = new Inventory(item.getProductId(), item.getProductName(), 100);
                inventoryRepository.save(inventory);
            }
        }

        boolean allAvailable = true;
        for (OrderCreatedEvent.OrderItemDetail item : event.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProductId()).orElse(null);
            if (inventory == null || !inventory.canReserve(item.getQuantity())) {
                allAvailable = false;
                break;
            }
        }

        if (allAvailable) {
            for (OrderCreatedEvent.OrderItemDetail item : event.getItems()) {
                Inventory inventory = inventoryRepository.findByProductId(item.getProductId()).get();
                inventory.reserve(item.getQuantity());
                inventoryRepository.save(inventory);
                
                StockEvent stockEvent = new StockEvent(
                    event.getOrderId(), event.getUserId(), item.getProductId(),
                    item.getQuantity(), "StockReserved"
                );
                rabbitTemplate.convertAndSend("app.exchange", "stock.reserved", stockEvent);
            }
            log.info("Stock reserved for order: {}", event.getOrderId());
        } else {
            StockEvent stockEvent = new StockEvent(
                event.getOrderId(), event.getUserId(), null, 0, "StockFailed"
            );
            rabbitTemplate.convertAndSend("app.exchange", "stock.failed", stockEvent);
            log.info("Stock failed for order: {}", event.getOrderId());
        }
    }
}