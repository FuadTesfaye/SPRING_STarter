package com.eventdriven.inventoryservice.infrastructure.messaging;

import com.eventdriven.inventoryservice.application.dto.StockReservedEvent;
import com.eventdriven.inventoryservice.application.dto.StockFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class RabbitMQSender {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${app.exchange}")
    private String exchange;
    
    @Value("${app.stock-reserved-routing}")
    private String stockReservedRouting;
    
    @Value("${app.stock-failed-routing}")
    private String stockFailedRouting;
    
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendStockReservedEvent(Long orderId, String product, Integer quantity) {
        StockReservedEvent event = new StockReservedEvent(orderId, product, quantity, LocalDateTime.now());
        log.info("Sending StockReserved event: {}", event);
        rabbitTemplate.convertAndSend(exchange, stockReservedRouting, event);
    }
    
    public void sendStockFailedEvent(Long orderId, String reason) {
        StockFailedEvent event = new StockFailedEvent(orderId, reason, LocalDateTime.now());
        log.info("Sending StockFailed event: {}", event);
        rabbitTemplate.convertAndSend(exchange, stockFailedRouting, event);
    }
}