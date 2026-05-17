package com.school.shipping.infrastructure.config;

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

    public static final String SHIPPING_PAYMENT_QUEUE = "shipping.payment.queue";
    public static final String SHIPPING_STOCK_QUEUE   = "shipping.stock.queue";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public Queue shippingPaymentQueue() {
        return QueueBuilder.durable(SHIPPING_PAYMENT_QUEUE).build();
    }

    @Bean
    public Queue shippingStockQueue() {
        return QueueBuilder.durable(SHIPPING_STOCK_QUEUE).build();
    }

    @Bean
    public Binding shippingPaymentBinding(Queue shippingPaymentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingPaymentQueue).to(appExchange).with("payment.completed");
    }

    @Bean
    public Binding shippingStockBinding(Queue shippingStockQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingStockQueue).to(appExchange).with("stock.reserved");
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
