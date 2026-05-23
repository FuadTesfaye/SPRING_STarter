package com.example.paymentservice.application.port;
public interface EventPublisherPort { void publish(String routingKey, Object payload); }
