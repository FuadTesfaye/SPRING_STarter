package com.example.shipping.application.service;

import com.example.shipping.domain.model.Shipping;
import com.example.shipping.domain.port.ShippingRepositoryPort;
import com.example.shipping.infrastructure.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private final ShippingRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public ShippingService(ShippingRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createShipping(String username) {
        Shipping shipping = new Shipping(username, "SHIPPED");
        repository.save(shipping);
        System.out.println("Shipping created for: " + username);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.SHIPPING_CREATED_EXCHANGE,
            RabbitMQConfig.SHIPPING_CREATED_ROUTING_KEY,
            username
        );
    }
}
