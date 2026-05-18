package com.app.shipping.infrastructure.messaging;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    
    public static final String PAYMENT_COMPLETED_QUEUE = "shipping.payment.completed.queue";
    public static final String PAYMENT_COMPLETED_ROUTING_KEY = "payment.completed";
    
    public static final String STOCK_RESERVED_QUEUE = "shipping.stock.reserved.queue";
    public static final String STOCK_RESERVED_ROUTING_KEY = "stock.reserved";
    
    public static final String SHIPMENT_CREATED_ROUTING_KEY = "shipment.created";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue(PAYMENT_COMPLETED_QUEUE);
    }

    @Bean
    public Binding paymentCompletedBinding(Queue paymentCompletedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(paymentCompletedQueue).to(exchange).with(PAYMENT_COMPLETED_ROUTING_KEY);
    }

    @Bean
    public Queue stockReservedQueue() {
        return new Queue(STOCK_RESERVED_QUEUE);
    }

    @Bean
    public Binding stockReservedBinding(Queue stockReservedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(stockReservedQueue).to(exchange).with(STOCK_RESERVED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
