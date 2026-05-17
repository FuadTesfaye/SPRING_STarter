package com.example.order.domain.event;

public interface EventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
}