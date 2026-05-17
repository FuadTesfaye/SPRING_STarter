package com.example.inventoryservice.application.port;
public interface EventPublisherPort { void publish(String routingKey, Object payload); }
