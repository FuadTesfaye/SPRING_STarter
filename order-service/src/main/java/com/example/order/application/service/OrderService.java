package com.example.order.application.service;

import com.example.order.domain.model.Order;
import com.example.order.domain.port.OrderRepositoryPort;
import com.example.order.infrastructure.messaging.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepositoryPort repository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepositoryPort repository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void createOrder(String username) {
        Order order = new Order(username, "CREATED");
        repository.save(order);
        System.out.println("Order created for: " + username);
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.ORDER_CREATED_EXCHANGE,
            RabbitMQConfig.ORDER_CREATED_ROUTING_KEY,
            username
        );
    }
}
