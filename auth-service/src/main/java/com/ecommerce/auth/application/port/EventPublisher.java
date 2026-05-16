package com.ecommerce.auth.application.port;

/**
 * Output port for publishing domain events.
 * Implemented in the Infrastructure layer (RabbitMQ).
 */
public interface EventPublisher {
    void publish(String routingKey, Object event);
}
