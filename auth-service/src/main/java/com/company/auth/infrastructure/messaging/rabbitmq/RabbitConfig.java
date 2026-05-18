package com.company.auth.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(RabbitUserProducer.EXCHANGE);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue("notification.queue");
    }

    @Bean
    public Binding binding(Queue notificationQueue, TopicExchange authExchange) {
        return BindingBuilder.bind(notificationQueue).to(authExchange).with("user.#");
    }
    
    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }
}
