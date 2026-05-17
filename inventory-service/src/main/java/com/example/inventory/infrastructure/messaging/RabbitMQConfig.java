package com.example.inventory.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String PAYMENT_PROCESSED_QUEUE = "payment.processed.queue";
    public static final String PAYMENT_PROCESSED_EXCHANGE = "payment.processed.exchange";
    public static final String PAYMENT_PROCESSED_ROUTING_KEY = "payment.processed";

    public static final String INVENTORY_UPDATED_QUEUE = "inventory.updated.queue";
    public static final String INVENTORY_UPDATED_EXCHANGE = "inventory.updated.exchange";
    public static final String INVENTORY_UPDATED_ROUTING_KEY = "inventory.updated";

    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue(PAYMENT_PROCESSED_QUEUE, true);
    }

    @Bean
    public TopicExchange paymentProcessedExchange() {
        return new TopicExchange(PAYMENT_PROCESSED_EXCHANGE);
    }

    @Bean
    public Binding paymentProcessedBinding() {
        return BindingBuilder.bind(paymentProcessedQueue())
                .to(paymentProcessedExchange())
                .with(PAYMENT_PROCESSED_ROUTING_KEY);
    }

    @Bean
    public Queue inventoryUpdatedQueue() {
        return new Queue(INVENTORY_UPDATED_QUEUE, true);
    }

    @Bean
    public TopicExchange inventoryUpdatedExchange() {
        return new TopicExchange(INVENTORY_UPDATED_EXCHANGE);
    }

    @Bean
    public Binding inventoryUpdatedBinding() {
        return BindingBuilder.bind(inventoryUpdatedQueue())
                .to(inventoryUpdatedExchange())
                .with(INVENTORY_UPDATED_ROUTING_KEY);
    }
}
