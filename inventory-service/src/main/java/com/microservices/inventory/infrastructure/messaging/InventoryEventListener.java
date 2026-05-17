package com.microservices.inventory.infrastructure.messaging;

import com.microservices.inventory.application.service.InventoryApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(InventoryEventListener.class);
    private final InventoryApplicationService applicationService;
    
    public InventoryEventListener(InventoryApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    @RabbitListener(queues = "${app.rabbitmq.queue.order-created}")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("========================================");
        log.info("📦 Inventory Service: Received order.created");
        log.info("   Order ID: {}", event.getOrderId());
        log.info("   Items: {}", event.getItems().size());
        log.info("========================================");
        
        // Check stock for each item
        event.getItems().forEach(item -> {
            applicationService.checkAndReserve(
                event.getOrderId(),
                item.getProductId(),
                item.getProductName(),
                item.getQuantity()
            );
        });
    }
}
