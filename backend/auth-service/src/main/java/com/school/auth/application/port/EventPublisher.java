package com.school.auth.application.port;

// Port for publishing domain events
public interface EventPublisher {
    void publish(String routingKey, Object event);
}
