package com.example.shipping.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange("app.exchange");
    }

    @Bean
    public Queue shippingPaymentQueue() {
        return new Queue("shipping.payment.queue", true);
    }

    @Bean
    public Binding shippingPaymentBinding() {
        return BindingBuilder.bind(shippingPaymentQueue())
                .to(appExchange())
                .with("payment.completed");
    }

    @Bean
    public Queue shippingStockQueue() {
        return new Queue("shipping.stock.queue", true);
    }

    @Bean
    public Binding shippingStockBinding() {
        return BindingBuilder.bind(shippingStockQueue())
                .to(appExchange())
                .with("stock.reserved");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}