package com.example.inventory.infrastructure.config;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.QueueNames;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EventExchange.APP_EXCHANGE);
    }

    @Bean
    public Queue inventoryOrderCreatedQueue() {
        return new Queue(QueueNames.INVENTORY_ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Binding inventoryOrderCreatedBinding(Queue inventoryOrderCreatedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(inventoryOrderCreatedQueue)
                .to(appExchange)
                .with(EventRoutingKeys.ORDER_CREATED);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
