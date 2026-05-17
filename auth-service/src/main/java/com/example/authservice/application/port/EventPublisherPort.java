package com.example.authservice.application.port;
public interface EventPublisherPort {
    void publish(String routingKey, Object payload);
}
