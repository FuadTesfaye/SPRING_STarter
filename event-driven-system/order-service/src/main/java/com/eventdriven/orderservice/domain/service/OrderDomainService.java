package com.eventdriven.orderservice.domain.service;

import com.eventdriven.orderservice.domain.model.Order;
import com.eventdriven.orderservice.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class OrderDomainService {
    
    private final OrderRepository orderRepository;
    
    public OrderDomainService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public Order createOrder(Long userId, String product, Integer quantity, BigDecimal price) {
        Order order = Order.builder()
                .userId(userId)
                .product(product)
                .quantity(quantity)
                .price(price)
                .status("CREATED")
                .createdAt(LocalDateTime.now())
                .build();
        return orderRepository.save(order);
    }
    
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}