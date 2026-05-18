package com.example.payment.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitPaymentConfig {
    public static final String EXCHANGE = "app.exchange";
    public static final String QUEUE_ORDER_CREATED = "payment.order.created.queue";

    @Bean
    public Queue orderCreatedQueue() { return new Queue(QUEUE_ORDER_CREATED); }

    @Bean
    public TopicExchange exchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public Binding binding(Queue orderCreatedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderCreatedQueue).to(exchange).with("order.created");
    }
}