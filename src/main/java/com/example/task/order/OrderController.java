package com.example.task.order;

import com.example.task.order.dto.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@Tag(name = "Order Service")
public class OrderController {

    private final RabbitTemplate rabbitTemplate;

    public OrderController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/orders")
    @Operation(summary = "Create order and publish order.created event")
    public String createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = UUID.randomUUID().toString();

        String eventPayload = "ORDER_CREATED:ID=" + orderId + ",CUSTOMER=" + request.getCustomerId();

        // Broadcast to the exact exchange and routing key from the assignment sheet
        rabbitTemplate.convertAndSend("app.exchange", "order.created", eventPayload);

        return "Order created successfully with ID: " + orderId;
    }
}
