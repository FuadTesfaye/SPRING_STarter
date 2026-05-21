package com.ecommerce.order.application.service;

import com.ecommerce.order.application.ports.EventPublisher;
import com.ecommerce.order.domain.entity.Order;
import com.ecommerce.order.domain.repository.OrderRepository;
import com.ecommerce.shared.messaging.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderApplicationService {

    private final EventPublisher eventPublisher;
    private final OrderRepository orderRepository;

    public String createOrder(String userId, BigDecimal amount) {
        String orderId = UUID.randomUUID().toString();
        
        Order order = Order.builder()
                .id(orderId)
                .userId(userId)
                .totalAmount(amount)
                .status(Order.OrderStatus.CREATED)
                .build();
                
        // Save to DB
        orderRepository.save(order);
        
        // Publish event to trigger Payment and Inventory
        eventPublisher.publish(new OrderCreatedEvent(orderId, userId, amount));
        
        return orderId;
    }
}
