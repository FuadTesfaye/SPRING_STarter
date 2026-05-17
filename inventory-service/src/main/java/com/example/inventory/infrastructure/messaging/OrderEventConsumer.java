package com.example.inventory.infrastructure.messaging;

import com.example.inventory.application.service.InventoryService;
import com.example.inventory.domain.event.OrderCreatedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(OrderEventConsumer.class);
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper;

    public OrderEventConsumer(InventoryService inventoryService, ObjectMapper objectMapper) {
        this.inventoryService = inventoryService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "inventory.order.queue")
    public void handleOrderCreated(String message) {
        try {
            log.info("Received order created event for inventory");
            OrderCreatedEvent event = objectMapper.readValue(message, OrderCreatedEvent.class);
            inventoryService.checkAndReserveStock(event);
        } catch (Exception e) {
            log.error("Error processing order event: {}", e.getMessage());
        }
    }
}