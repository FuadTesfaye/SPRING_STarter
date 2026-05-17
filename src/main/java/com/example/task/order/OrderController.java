package com.example.task.order;

import com.example.task.order.dto.CreateOrderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    public OrderController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/orders")
    public String createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        String eventMessage = "Order " + orderId + " created for Customer " + request.getCustomerId();

        rabbitTemplate.convertAndSend("notificationQueue", eventMessage);

        return "Order created successfully with ID: " + orderId;
    }
}
