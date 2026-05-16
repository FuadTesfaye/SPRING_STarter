package com.ecommerce.order.application.port;

public interface EventPublisher {
    void publish(String routingKey, Object event);
}
