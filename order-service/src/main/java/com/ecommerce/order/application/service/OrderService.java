package com.ecommerce.order.application.service;

import com.ecommerce.order.application.port.EventPublisher;
import com.ecommerce.order.domain.event.OrderCreatedEvent;
import com.ecommerce.order.domain.model.Order;
import com.ecommerce.order.domain.model.OrderStatus;
import com.ecommerce.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public Order createOrder(String userId, String productId, Integer quantity,
                             BigDecimal amount, String shippingAddress) {
        Order order = Order.builder()
                .userId(userId)
                .productId(productId)
                .quantity(quantity)
                .amount(amount)
                .shippingAddress(shippingAddress)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        Order saved = orderRepository.save(order);
        log.info("Order created: {}", saved.getId());

        eventPublisher.publish("order.created", OrderCreatedEvent.builder()
                .orderId(saved.getId())
                .userId(saved.getUserId())
                .productId(saved.getProductId())
                .quantity(saved.getQuantity())
                .amount(saved.getAmount())
                .shippingAddress(saved.getShippingAddress())
                .timestamp(LocalDateTime.now())
                .build());

        return saved;
    }

    public Optional<Order> findById(String id) { return orderRepository.findById(id); }
    public List<Order> findAll() { return orderRepository.findAll(); }
    public List<Order> findByUserId(String userId) { return orderRepository.findByUserId(userId); }
}
