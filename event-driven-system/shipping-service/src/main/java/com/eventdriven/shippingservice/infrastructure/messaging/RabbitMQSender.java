package com.eventdriven.shippingservice.infrastructure.messaging;

import com.eventdriven.shippingservice.application.dto.ShipmentCreatedEvent;
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
    
    @Value("${app.shipment-created-routing}")
    private String shipmentCreatedRouting;
    
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendShipmentCreatedEvent(Long orderId, Long shipmentId, String trackingNumber) {
        ShipmentCreatedEvent event = new ShipmentCreatedEvent(orderId, shipmentId, trackingNumber, LocalDateTime.now());
        log.info("Sending ShipmentCreated event: {}", event);
        rabbitTemplate.convertAndSend(exchange, shipmentCreatedRouting, event);
    }
}