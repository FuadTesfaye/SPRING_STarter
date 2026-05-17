package com.eventdriven.inventoryservice.application.service;

import com.eventdriven.inventoryservice.application.dto.OrderCreatedEvent;
import com.eventdriven.inventoryservice.domain.service.InventoryDomainService;
import com.eventdriven.inventoryservice.infrastructure.messaging.RabbitMQSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class InventoryApplicationService {
    
    private static final Logger log = LoggerFactory.getLogger(InventoryApplicationService.class);
    private final InventoryDomainService inventoryDomainService;
    private final RabbitMQSender rabbitMQSender;
    
    public InventoryApplicationService(InventoryDomainService inventoryDomainService, RabbitMQSender rabbitMQSender) {
        this.inventoryDomainService = inventoryDomainService;
        this.rabbitMQSender = rabbitMQSender;
    }
    
    public void processOrder(OrderCreatedEvent event) {
        log.info("Checking stock for order: {}", event.getOrderId());
        
        boolean stockAvailable = inventoryDomainService.checkStock(event.getProduct(), event.getQuantity());
        
        if (stockAvailable) {
            inventoryDomainService.reserveStock(event.getProduct(), event.getQuantity());
            rabbitMQSender.sendStockReservedEvent(event.getOrderId(), event.getProduct(), event.getQuantity());
            log.info("Stock reserved for order: {}", event.getOrderId());
        } else {
            rabbitMQSender.sendStockFailedEvent(event.getOrderId(), "Insufficient stock for " + event.getProduct());
            log.info("Stock failed for order: {}", event.getOrderId());
        }
    }
}