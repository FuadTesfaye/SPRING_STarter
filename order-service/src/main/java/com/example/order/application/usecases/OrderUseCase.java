package com.example.order.application.usecases;

import com.example.order.application.dto.OrderRequest;
import com.example.order.application.ports.OrderEventPublisher;
import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderUseCase {
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public void createOrder(OrderRequest request) {
        // 1. Create Domain Object
        Order order = new Order(null, request.userId(), request.productId(),
                request.quantity(), request.price(), "CREATED");

        // 2. Save via Port
        Order savedOrder = orderRepository.save(order);

        // 3. Publish Event via Port
        eventPublisher.publishOrderCreated(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getPrice() * savedOrder.getQuantity()
        );
    }
}