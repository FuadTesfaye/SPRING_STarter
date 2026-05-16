package com.example.task.notification;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue notificationQueue() {
        // This tells RabbitMQ to automatically spin up a data channel named "notificationQueue"
        return new Queue("notificationQueue", true);
    }
}
