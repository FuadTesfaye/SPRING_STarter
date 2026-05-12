package com.microservices.paymentservice.infrastructure.messaging;

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
    public Queue orderQueue() {
        return new Queue("order.created.queue");
    }

    @Bean
    public Binding binding(
            Queue orderQueue,
            TopicExchange exchange) {

        return BindingBuilder
                .bind(orderQueue)
                .to(exchange)
                .with("order.created");
    }
}