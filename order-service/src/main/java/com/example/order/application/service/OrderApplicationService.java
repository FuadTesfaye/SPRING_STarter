package com.example.order.application.service;

import com.example.order.application.dto.CreateOrderCommand;
import com.example.order.application.dto.OrderResponse;
import com.example.order.application.exception.OrderNotFoundException;
import com.example.order.application.port.out.OrderEventPublisher;
import com.example.order.application.port.out.OrderRepository;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderApplicationService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public OrderApplicationService(OrderRepository orderRepository, OrderEventPublisher orderEventPublisher) {
        this.orderRepository = orderRepository;
        this.orderEventPublisher = orderEventPublisher;
    }

    @Transactional
    public OrderResponse createOrder(CreateOrderCommand command) {
        Order savedOrder = orderRepository.save(new Order(
                null,
                command.userId(),
                command.productId(),
                command.quantity(),
                command.totalAmount(),
                OrderStatus.CREATED,
                Instant.now()
        ));

        publishOrderCreatedAsync(savedOrder);
        return toResponse(savedOrder);
    }

    @Async
    private void publishOrderCreatedAsync(Order order) {
        try {
            orderEventPublisher.publishOrderCreated(order);
            System.out.println("📦 Order created event published successfully");
        } catch (Exception e) {
            System.err.println("⚠️ Failed to publish order created event: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::toResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order not found: " + orderId));
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getProductId(),
                order.getQuantity(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
