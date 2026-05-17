package com.microservices.auth.domain.event;

/**
 * DOMAIN EVENT PUBLISHER INTERFACE
 * 
 * WHAT: Contract for publishing domain events
 * WHY: Domain doesn't know about RabbitMQ/Message Brokers
 * HOW: Infrastructure layer implements with actual messaging
 */
public interface EventPublisher {
    
    /**
     * Publish an event to notify other services
     * 
     * @param event The domain event to publish
     */
    void publish(UserRegisteredEvent event);
}
