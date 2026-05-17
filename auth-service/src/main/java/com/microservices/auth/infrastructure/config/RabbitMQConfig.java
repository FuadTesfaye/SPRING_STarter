package com.microservices.auth.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RABBITMQ CONFIGURATION
 * 
 * WHAT: Sets up RabbitMQ exchange, queues, and bindings
 * WHY: 
 * - Define messaging infrastructure
 * - Configure message serialization (JSON)
 * - Enable reliable messaging
 * 
 * KEY CONCEPTS:
 * - Exchange: Routes messages (like a post office)
 * - Queue: Stores messages (like a mailbox)
 * - Binding: Connects queues to exchange (like mail routes)
 * - Routing Key: Addresses messages to specific queues
 */
@Configuration
public class RabbitMQConfig {
    
    // Exchange name
    public static final String EXCHANGE_NAME = "app.exchange";
    
    // Queue names
    public static final String USER_REGISTERED_QUEUE = "user.registered.queue";
    
    // Routing keys
    public static final String USER_REGISTERED_KEY = "user.registered";
    
    /**
     * Create the Topic Exchange
     * 
     * Topic Exchange: Routes messages based on routing key patterns
     * Like a smart post office that reads addresses
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }
    
    /**
     * Create Queue for user registration events
     */
    @Bean
    public Queue userRegisteredQueue() {
        return new Queue(USER_REGISTERED_QUEUE, true); // durable queue
    }
    
    /**
     * Bind queue to exchange with routing key
     * 
     * This means: Messages sent to EXCHANGE_NAME with routing key 
     * USER_REGISTERED_KEY will be delivered to USER_REGISTERED_QUEUE
     */
    @Bean
    public Binding userRegisteredBinding() {
        return BindingBuilder
            .bind(userRegisteredQueue())
            .to(exchange())
            .with(USER_REGISTERED_KEY);
    }
    
    /**
     * Configure RabbitTemplate to send messages as JSON
     * 
     * Without this, messages would be Java serialized (not readable)
     * With this, messages are JSON (universal format)
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
    
    /**
     * Configure message converter for receiving messages
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
