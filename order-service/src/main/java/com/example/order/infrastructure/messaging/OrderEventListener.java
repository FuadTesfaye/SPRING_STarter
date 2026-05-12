package com.microservices.orderservice.infrastructure.messaging;

import com.microservices.orderservice.application.event.UserRegisteredEvent;
import com.microservices.orderservice.application.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    private final OrderService orderService;

    public OrderEventListener(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @RabbitListener(
            queues = "user.registered.queue")
    public void handle(
            UserRegisteredEvent event) {

        orderService.createOrder(
                event.getUsername());
    }
}