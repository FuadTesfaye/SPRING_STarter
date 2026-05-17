package com.example.order.application.service;

import com.example.order.application.dto.CreateOrderRequest;
import com.example.order.application.dto.OrderResponse;
import com.example.order.domain.event.EventPublisher;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.model.Order;
import com.example.order.domain.model.OrderItem;
import com.example.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, EventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public OrderResponse createOrder(CreateOrderRequest request) {
        List<OrderItem> orderItems = request.getItems().stream()
                .map(item -> new OrderItem(
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getPrice()
                ))
                .collect(Collectors.toList());

        Order order = new Order.Builder()
                .userId(request.getUserId())
                .items(orderItems)
                .build();

        order = orderRepository.save(order);

        List<OrderCreatedEvent.OrderItemDetail> itemDetails = order.getItems().stream()
                .map(item -> new OrderCreatedEvent.OrderItemDetail(
                    item.getProductId(),
                    item.getProductName(),
                    item.getQuantity(),
                    item.getPrice()
                ))
                .collect(Collectors.toList());

        OrderCreatedEvent event = new OrderCreatedEvent(
            order.getId(), order.getUserId(), order.getTotalAmount(), itemDetails
        );
        eventPublisher.publishOrderCreated(event);

        return OrderResponse.fromDomain(order);
    }

    public List<OrderResponse> getUserOrders(UUID userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(OrderResponse::fromDomain)
                .collect(Collectors.toList());
    }
}