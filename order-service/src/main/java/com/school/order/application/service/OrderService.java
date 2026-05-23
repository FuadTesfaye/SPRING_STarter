package com.school.order.application.service;

import com.school.order.application.port.EventPublisher;
import com.school.order.application.port.OrderRepository;
import com.school.order.domain.entity.Order;
import com.school.order.domain.event.OrderCreatedEvent;
import com.school.order.presentation.dto.CreateOrderRequest;
import com.school.order.presentation.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final EventPublisher eventPublisher;

    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = new Order(
            request.getStudentId(),
            request.getStudentName(),
            request.getFeeType(),
            request.getAmount()
        );
        Order saved = orderRepository.save(order);

        // Publish event so Payment and Inventory services can react
        OrderCreatedEvent event = new OrderCreatedEvent(
            saved.getId(),
            saved.getStudentId(),
            saved.getStudentName(),
            saved.getFeeType(),
            saved.getAmount()
        );
        eventPublisher.publish("order.created", event);

        return toResponse(saved);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getStudentId(),
            order.getStudentName(),
            order.getFeeType(),
            order.getAmount(),
            order.getStatus(),
            order.getCreatedAt()
        );
    }
}
