package com.example.order.application.service;

import com.example.order.domain.model.Order;
import com.example.order.domain.repository.OrderRepository;
import com.example.order.domain.port.OrderEventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCreationService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisherPort orderEventPublisher;

    public Order createOrder(Long userId, String productId, int quantity, double unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity must be positive");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("unitPrice must be non-negative");
        }
        String orderId = UUID.randomUUID().toString();
        double total = unitPrice * quantity;
        Order order = new Order(orderId, productId, quantity, total, "PENDING");
        Order saved = orderRepository.save(order);
        orderEventPublisher.publishOrderCreated(saved, userId);
        return saved;
    }
}
