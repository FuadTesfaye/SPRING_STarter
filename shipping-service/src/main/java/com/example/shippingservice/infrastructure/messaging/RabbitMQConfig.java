package com.example.shippingservice.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";

    public static final String PAYMENT_COMPLETED_KEY = "payment.completed";
    public static final String STOCK_RESERVED_KEY = "stock.reserved";
    public static final String SHIPMENT_CREATED_KEY = "shipment.created";

    public static final String SHIPPING_QUEUE = "shipping.queue";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue shippingQueue() {
        return QueueBuilder.durable(SHIPPING_QUEUE).build();
    }

    // Binding for Payment Completed
    @Bean
    public Binding paymentCompletedBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue)
                .to(appExchange)
                .with(PAYMENT_COMPLETED_KEY);
    }

    // Binding for Stock Reserved
    @Bean
    public Binding stockReservedBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue)
                .to(appExchange)
                .with(STOCK_RESERVED_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}