package com.company.beautyprofile.infrastructure.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE = "order.completed.profile.q";

    @Bean
    public Queue profileQueue() {
        return new Queue(QUEUE);
    }
}
