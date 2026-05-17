package com.app.order.application.usecases;

import com.app.order.application.dto.OrderRequest;
import com.app.order.application.dto.OrderResponse;
import com.app.order.application.ports.OrderService;
import com.app.order.domain.Order;
import com.app.order.domain.OrderRepository;
import com.app.order.domain.OrderStatus;
import com.app.order.application.ports.EventPublisher;

import java.util.UUID;

public class CreateOrderUseCase implements OrderService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public CreateOrderUseCase(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        Order order = new Order(
                UUID.randomUUID(),
                request.customerId(),
                request.amount(),
                OrderStatus.CREATED
        );

        Order savedOrder = orderRepository.save(order);

        // Publish OrderCreated event
        eventPublisher.publishOrderCreated(savedOrder);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus().name()
        );
    }
}
