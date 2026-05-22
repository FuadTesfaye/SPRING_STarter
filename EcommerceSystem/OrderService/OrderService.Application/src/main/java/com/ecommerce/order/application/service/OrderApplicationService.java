package com.ecommerce.order.application.service;

import com.ecommerce.order.application.dto.OrderRequest;
import com.ecommerce.order.application.dto.OrderResponse;
import com.ecommerce.order.application.ports.EventPublisher;
import com.ecommerce.order.application.usecase.CreateOrderUseCase;
import com.ecommerce.order.domain.entity.Order;
import com.ecommerce.order.domain.entity.OrderItem;
import com.ecommerce.order.domain.repository.OrderRepository;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderApplicationService implements CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        BigDecimal totalAmount = request.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = Order.builder()
                .id(UUID.randomUUID().toString())
                .userId(request.getUserId())
                .totalAmount(totalAmount)
                .status(Order.OrderStatus.CREATED)
                .items(request.getItems().stream().map(item -> OrderItem.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .price(item.getPrice())
                        .build()).collect(Collectors.toList()))
                .build();

        orderRepository.save(order);

        // Publish event to RabbitMQ
        eventPublisher.publish(new OrderCreatedEvent(order.getId(), order.getUserId(), order.getTotalAmount()));

        return OrderResponse.builder()
                .orderId(order.getId())
                .status(order.getStatus().name())
                .totalAmount(order.getTotalAmount())
                .message("Order created successfully")
                .build();
    }
}
