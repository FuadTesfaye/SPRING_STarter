package com.school.inventory.application.port;

public interface EventPublisher {
    void publish(String routingKey, Object event);
}
