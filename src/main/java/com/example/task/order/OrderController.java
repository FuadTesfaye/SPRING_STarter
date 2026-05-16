package com.example.task.order;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    public OrderController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/create-order")
    public String createOrder(@RequestParam String customerName, @RequestParam String item) {
        String eventMessage = "Order created for " + customerName + " buying " + item;

        rabbitTemplate.convertAndSend("notificationQueue", eventMessage);

        return "Order received successfully! Notification service will print it shortly.";
    }
}
