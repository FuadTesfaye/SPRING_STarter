package com.example.order.infrastructure.messaging;

import com.example.order.application.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final OrderService orderService;

    public OrderEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQConfig.USER_REGISTERED_QUEUE)
    public void handleUserRegistered(String username) {
        System.out.println("Order-service received user.registered for: " + username);
        orderService.createOrder(username);
    }
}
