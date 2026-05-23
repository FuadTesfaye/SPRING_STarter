package com.example.shippingservice.application.port;
public interface EventPublisherPort { void publish(String routingKey, Object payload); }
