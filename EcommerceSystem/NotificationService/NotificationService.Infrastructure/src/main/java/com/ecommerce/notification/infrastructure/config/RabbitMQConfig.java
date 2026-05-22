package com.ecommerce.notification.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String ALL_EVENTS_QUEUE = "notification.all.queue";
    public static final String ROUTING_KEY_PATTERN = "#"; // Listen to all topics

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue allEventsQueue() {
        return new Queue(ALL_EVENTS_QUEUE);
    }

    @Bean
    public Binding allEventsBinding(Queue allEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(allEventsQueue).to(appExchange).with(ROUTING_KEY_PATTERN);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
