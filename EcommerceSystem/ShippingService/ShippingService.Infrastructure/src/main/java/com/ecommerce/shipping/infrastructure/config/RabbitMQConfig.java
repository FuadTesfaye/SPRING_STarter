package com.ecommerce.shipping.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String SHIPPING_QUEUE = "shipping.queue";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue shippingQueue() {
        return new Queue(SHIPPING_QUEUE, true);
    }

    @Bean
    public Binding paymentCompletedBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue).to(appExchange).with("payment.completed");
    }

    @Bean
    public Binding stockReservedBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue).to(appExchange).with("stock.reserved");
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
