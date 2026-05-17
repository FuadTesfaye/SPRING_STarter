package com.eventdriven.orderservice.application.service;

import com.eventdriven.orderservice.application.dto.CreateOrderRequest;
import com.eventdriven.orderservice.application.dto.OrderCreatedEvent;
import com.eventdriven.orderservice.application.dto.OrderResponse;
import com.eventdriven.orderservice.domain.model.Order;
import com.eventdriven.orderservice.domain.service.OrderDomainService;
import com.eventdriven.orderservice.infrastructure.messaging.RabbitMQSender;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrderApplicationService {
    
    private final OrderDomainService orderDomainService;
    private final RabbitMQSender rabbitMQSender;
    
    public OrderApplicationService(OrderDomainService orderDomainService, RabbitMQSender rabbitMQSender) {
        this.orderDomainService = orderDomainService;
        this.rabbitMQSender = rabbitMQSender;
    }
    
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = orderDomainService.createOrder(
                request.getUserId(),
                request.getProduct(),
                request.getQuantity(),
                request.getPrice()
        );
        
        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .timestamp(LocalDateTime.now())
                .build();
        
        rabbitMQSender.sendOrderCreatedEvent(event);
        
        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .product(order.getProduct())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();
    }
}