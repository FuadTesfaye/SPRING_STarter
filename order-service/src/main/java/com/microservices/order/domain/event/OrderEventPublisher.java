package com.microservices.order.domain.event;

public interface OrderEventPublisher {
    void publishOrderCreated(OrderCreatedEvent event);
}
