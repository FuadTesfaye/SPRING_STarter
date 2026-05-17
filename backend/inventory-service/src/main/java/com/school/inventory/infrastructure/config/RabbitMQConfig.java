package com.school.inventory.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String INVENTORY_DLQ   = "inventory.queue.dlq";
    public static final String DLX             = "app.exchange.dlx";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX, true, false);
    }

    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(INVENTORY_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", "dlq.inventory")
            .build();
    }

    @Bean
    public Queue inventoryDlq() {
        return QueueBuilder.durable(INVENTORY_DLQ).build();
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(inventoryQueue).to(appExchange).with("order.created");
    }

    @Bean
    public Binding inventoryDlqBinding(Queue inventoryDlq, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(inventoryDlq).to(deadLetterExchange).with("dlq.inventory");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
