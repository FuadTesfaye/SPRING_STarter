package com.example.inventservice.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";

    // Routing Keys
    public static final String ORDER_CREATED_KEY = "order.created";
    public static final String STOCK_RESERVED_KEY = "stock.reserved";
    public static final String STOCK_FAILED_KEY = "stock.failed";

    // Queue
    public static final String ORDER_CREATED_QUEUE = "inventory.order.created.queue";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue orderCreatedQueue() {
        return QueueBuilder.durable(ORDER_CREATED_QUEUE).build();
    }

    @Bean
    public Binding orderCreatedBinding(Queue orderCreatedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(orderCreatedQueue)
                .to(appExchange)
                .with(ORDER_CREATED_KEY);
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter);
        template.setMandatory(true);   // Important for returns

        // Correct modern Return Callback
        template.setReturnsCallback(returned -> {
            System.err.println("❌ [RabbitMQ] Message RETURNED (not routed)!");
            System.err.println("   Exchange    : " + returned.getExchange());
            System.err.println("   Routing Key : " + returned.getRoutingKey());
            System.err.println("   Message     : " + returned.getMessage());
        });

        // Optional: Confirm Callback
        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                System.err.println("❌ [RabbitMQ] Message NOT confirmed! Cause: " + cause);
            }
        });

        return template;
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}