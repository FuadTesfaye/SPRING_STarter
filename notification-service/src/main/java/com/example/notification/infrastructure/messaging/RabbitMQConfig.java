package com.microservices.notificationservice.infrastructure.messaging;

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
    public Queue shippingQueue() {
        return new Queue("shipping.created.queue");
    }

    @Bean
    public Binding binding(
            Queue shippingQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(shippingQueue)
                .to(exchange)
                .with("shipping.created");
    }
}