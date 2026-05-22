package com.ecommerce.shipping.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String PAYMENT_COMPLETED_QUEUE = "shipping.payment_completed.queue";
    public static final String STOCK_RESERVED_QUEUE = "shipping.stock_reserved.queue";
    
    public static final String ROUTING_KEY_PAYMENT_COMPLETED = "payment.completed";
    public static final String ROUTING_KEY_STOCK_RESERVED = "stock.reserved";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue(PAYMENT_COMPLETED_QUEUE);
    }

    @Bean
    public Queue stockReservedQueue() {
        return new Queue(STOCK_RESERVED_QUEUE);
    }

    @Bean
    public Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentCompletedQueue).to(appExchange).with(ROUTING_KEY_PAYMENT_COMPLETED);
    }

    @Bean
    public Binding stockReservedBinding(Queue stockReservedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(stockReservedQueue).to(appExchange).with(ROUTING_KEY_STOCK_RESERVED);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
