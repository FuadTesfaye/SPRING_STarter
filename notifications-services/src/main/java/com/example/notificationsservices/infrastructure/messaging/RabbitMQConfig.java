package com.example.notificationsservices.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "app.exchange";
    public static final String QUEUE_NAME = "notification.queue";

    // Exchange (must match all services)
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    // Queue
    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    // Binding (THIS IS YOUR CODE)
    @Bean
    public Binding bindNotificationQueue(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with("#"); // listens to ALL routing keys
    }
}