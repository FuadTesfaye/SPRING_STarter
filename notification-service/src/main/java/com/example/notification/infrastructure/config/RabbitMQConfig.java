package com.example.notification.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange("app.exchange");
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notification.all.queue", true);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(appExchange())
                .with("#"); // Listen to ALL events
    }
}