package com.ecom.order.application.service;

import com.ecom.order.application.dto.*;
import com.ecom.order.application.port.EventPublisher;
import com.ecom.order.domain.model.Order;
import com.ecom.order.domain.model.OrderItem;
import com.ecom.order.domain.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderServiceImpl {
    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public OrderResponse createOrder(OrderRequest request) {
        List<OrderItem> domainItems = request.items().stream()
                .map(item -> new OrderItem(
                        item.productId(),
                        item.productName(),
                        item.quantity(),
                        item.price()
                ))
                .collect(Collectors.toList());

        Order order = new Order(
                UUID.randomUUID(),
                request.customerId(),
                domainItems,
                "CREATED",
                LocalDateTime.now()
        );
        order.validate();

        Order savedOrder = orderRepository.save(order);

        // Publish event
        eventPublisher.publishOrderCreated(new OrderCreatedEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount()
        ));

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus()
        );
    }
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> new OrderResponse(
                        order.getId(),
                        order.getCustomerId(),
                        order.getTotalAmount(),
                        order.getStatus()
                ))
                .collect(Collectors.toList());
    }
}
