package com.company.shipping.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String QUEUE = "shipping.queue";
    public static final String PAYMENT_QUEUE = "shipping.payment.queue";

    @Bean
    public Queue shippingQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue paymentQueue() {
        return new Queue(PAYMENT_QUEUE);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding shippingBinding(Queue shippingQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(shippingQueue).to(orderExchange).with("order.created");
    }

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }
}
