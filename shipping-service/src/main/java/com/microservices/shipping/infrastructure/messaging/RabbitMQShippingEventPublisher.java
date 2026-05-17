package com.microservices.shipping.infrastructure.messaging;

import com.microservices.shipping.domain.event.ShipmentCreatedEvent;
import com.microservices.shipping.domain.event.ShippingEventPublisher;
import com.microservices.shipping.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQShippingEventPublisher implements ShippingEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQShippingEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQShippingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publishShipmentCreated(ShipmentCreatedEvent event) {
        log.info("Publishing shipment.created: {}", event.getShipmentId());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.SHIPMENT_CREATED_KEY,
            event
        );
    }
}
