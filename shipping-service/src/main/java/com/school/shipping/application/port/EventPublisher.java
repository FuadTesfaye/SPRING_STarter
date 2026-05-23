package com.school.shipping.application.port;

public interface EventPublisher {
    void publish(String routingKey, Object event);
}
