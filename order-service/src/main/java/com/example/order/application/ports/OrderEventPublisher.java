package com.example.order.application.ports;

public interface OrderEventPublisher {
    void publishOrderCreated(Long orderId, Long userId, Double totalAmount);
}
