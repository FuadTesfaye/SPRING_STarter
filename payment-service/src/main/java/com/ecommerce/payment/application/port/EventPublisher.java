package com.ecommerce.payment.application.port;

public interface EventPublisher {
    void publish(String routingKey, Object event);
}
