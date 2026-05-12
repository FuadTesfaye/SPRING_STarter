package com.microservices.orderservice.application.service;

import com.microservices.orderservice.application.event.OrderCreatedEvent;
import com.microservices.orderservice.domain.model.Order;
import com.microservices.orderservice.domain.port.OrderRepositoryPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(
            OrderRepositoryPort repository,
            RabbitTemplate rabbitTemplate) {

        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createOrder(String username) {

        Order order =
                new Order(username, "CREATED");

        repository.save(order);

        rabbitTemplate.convertAndSend(
                "app.exchange",
                "order.created",
                new OrderCreatedEvent(username)
        );
    }
}