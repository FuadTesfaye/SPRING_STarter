package com.microservices.shippingservice.application.service;

import com.microservices.shippingservice.application.event.ShippingEvent;
import com.microservices.shippingservice.domain.model.Shipping;
import com.microservices.shippingservice.domain.port.ShippingRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ShippingService {

    private final ShippingRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public ShippingService(
            ShippingRepositoryPort repository,
            RabbitTemplate rabbitTemplate) {

        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createShipping(String username) {

        Shipping shipping =
                new Shipping(username, "SHIPPED");

        repository.save(shipping);

        rabbitTemplate.convertAndSend(
                "app.exchange",
                "shipping.created",
                new ShippingEvent(username)
        );
    }
}