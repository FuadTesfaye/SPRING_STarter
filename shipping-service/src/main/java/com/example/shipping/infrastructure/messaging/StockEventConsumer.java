package com.example.shipping.infrastructure.messaging;

import com.example.shipping.application.service.ShippingService;
import com.example.shipping.domain.event.StockReservedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockEventConsumer {
    private static final Logger log = LoggerFactory.getLogger(StockEventConsumer.class);
    private final ShippingService shippingService;
    private final ObjectMapper objectMapper;

    public StockEventConsumer(ShippingService shippingService, ObjectMapper objectMapper) {
        this.shippingService = shippingService;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "shipping.stock.queue")
    public void handleStockReserved(String message) {
        try {
            StockReservedEvent event = objectMapper.readValue(message, StockReservedEvent.class);
            shippingService.handleStockReserved(event.getOrderId(), event.getUserId());
        } catch (Exception e) {
            log.error("Error processing stock event: {}", e.getMessage());
        }
    }
}