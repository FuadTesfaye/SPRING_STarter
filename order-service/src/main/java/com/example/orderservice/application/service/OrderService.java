package com.example.orderservice.application.service;

import com.example.orderservice.application.dto.OrderCreatedEvent;
import com.example.orderservice.domain.model.Order;
import com.example.orderservice.domain.repository.OrderRepository;
import com.example.orderservice.infrastructure.messaging.OrderEventPublisher;
import com.example.orderservice.presentation.dto.OrderRequestDTO;
import com.example.orderservice.presentation.dto.OrderResponseDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderService(OrderRepository orderRepository,
                        OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    public OrderResponseDTO createOrder(OrderRequestDTO request) {

        // 1. Generate Order ID
        UUID orderId = UUID.randomUUID();

        // 2. Create Domain Model
        Order order = new Order(
                orderId,
                request.getProductName(),
                request.getQuantity(),
                "CREATED"
        );

        // 3. Save Order
        Order savedOrder = orderRepository.save(order);

        // 4. Create Event
        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getStatus()
        );

        // Optional additional data
        event.setUserId(1L);
        event.setTotalAmount(99.99 * request.getQuantity());

        // 5. Publish Event to RabbitMQ
        orderEventPublisher.publishOrderCreated(event);

        // 6. Return Response
        return new OrderResponseDTO(
                savedOrder.getId(),
                savedOrder.getProductName(),
                savedOrder.getQuantity(),
                savedOrder.getStatus()
        );
    }
}