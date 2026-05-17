package com.school.order.application.port;

public interface EventPublisher {
    void publish(String routingKey, Object event);
}
