package com.school.payment.infrastructure.config;

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

    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String PAYMENT_DLQ   = "payment.queue.dlq";
    public static final String DLX           = "app.exchange.dlx";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchange, true, false);
    }

    // Dead Letter Exchange
    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX, true, false);
    }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", "dlq.payment")
            .build();
    }

    @Bean
    public Queue paymentDlq() {
        return QueueBuilder.durable(PAYMENT_DLQ).build();
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentQueue).to(appExchange).with("order.created");
    }

    @Bean
    public Binding paymentDlqBinding(Queue paymentDlq, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(paymentDlq).to(deadLetterExchange).with("dlq.payment");
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
