package com.microservices.orderservice.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE =
            "app.exchange";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user.registered.queue");
    }

    @Bean
    public Binding binding(
            Queue userQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(userQueue)
                .to(exchange)
                .with("user.registered");
    }
}