package com.microservices.inventory.infrastructure.messaging;

import com.microservices.inventory.domain.event.InventoryEventPublisher;
import com.microservices.inventory.domain.event.StockFailedEvent;
import com.microservices.inventory.domain.event.StockReservedEvent;
import com.microservices.inventory.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQInventoryEventPublisher implements InventoryEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQInventoryEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQInventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publishStockReserved(StockReservedEvent event) {
        log.info("Publishing stock reserved: {}", event.getReservationId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, 
            RabbitMQConfig.STOCK_RESERVED_KEY, event);
    }
    
    @Override
    public void publishStockFailed(StockFailedEvent event) {
        log.info("Publishing stock failed: {}", event.getReservationId());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.STOCK_FAILED_KEY, event);
    }
}
