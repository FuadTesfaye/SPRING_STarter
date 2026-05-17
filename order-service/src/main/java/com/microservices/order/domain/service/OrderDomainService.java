package com.microservices.order.domain.service;

import com.microservices.order.domain.event.OrderCreatedEvent;
import com.microservices.order.domain.event.OrderEventPublisher;
import com.microservices.order.domain.exception.OrderNotFoundException;
import com.microservices.order.domain.model.Order;
import com.microservices.order.domain.model.OrderId;
import com.microservices.order.domain.model.OrderItem;
import com.microservices.order.domain.repository.OrderRepository;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDomainService {
    
    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;
    
    public OrderDomainService(OrderRepository orderRepository, 
                               OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }
    
    public Order createOrder(String userId, List<OrderItem> items) {
        Order order = Order.create(userId, items);
        Order savedOrder = orderRepository.save(order);
        
        List<OrderCreatedEvent.OrderItemDetail> itemDetails = savedOrder.getItems().stream()
            .map(item -> new OrderCreatedEvent.OrderItemDetail(
                item.getProductId(), item.getProductName(),
                item.getQuantity(), item.getPrice()))
            .collect(Collectors.toList());
        
        eventPublisher.publishOrderCreated(new OrderCreatedEvent(
            savedOrder.getId().getValue().toString(),
            savedOrder.getUserId(), itemDetails, savedOrder.getTotalAmount()));
        
        return savedOrder;
    }
    
    public Order findById(String orderId) {
        OrderId id = OrderId.fromString(orderId);
        return orderRepository.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
    
    public List<Order> findByUserId(String userId) {
        return orderRepository.findByUserId(userId);
    }
    
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
