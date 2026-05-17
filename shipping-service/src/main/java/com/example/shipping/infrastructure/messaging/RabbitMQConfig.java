package com.example.shipping.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String INVENTORY_UPDATED_QUEUE = "inventory.updated.queue";
    public static final String INVENTORY_UPDATED_EXCHANGE = "inventory.updated.exchange";
    public static final String INVENTORY_UPDATED_ROUTING_KEY = "inventory.updated";

    public static final String SHIPPING_CREATED_QUEUE = "shipping.created.queue";
    public static final String SHIPPING_CREATED_EXCHANGE = "shipping.created.exchange";
    public static final String SHIPPING_CREATED_ROUTING_KEY = "shipping.created";

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

    @Bean
    public Queue shippingCreatedQueue() {
        return new Queue(SHIPPING_CREATED_QUEUE, true);
    }

    @Bean
    public TopicExchange shippingCreatedExchange() {
        return new TopicExchange(SHIPPING_CREATED_EXCHANGE);
    }

    @Bean
    public Binding shippingCreatedBinding() {
        return BindingBuilder.bind(shippingCreatedQueue())
                .to(shippingCreatedExchange())
                .with(SHIPPING_CREATED_ROUTING_KEY);
    }
}
