package com.microservices.shippingservice.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange("app.exchange");
    }

    @Bean
    public Queue inventoryQueue() {
        return new Queue("inventory.updated.queue");
    }

    @Bean
    public Binding binding(
            Queue inventoryQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(inventoryQueue)
                .to(exchange)
                .with("inventory.updated");
    }
}