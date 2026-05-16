package com.ecommerce.payment.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String PAYMENT_DLQ = "payment.dlq";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", PAYMENT_DLQ)
                .build();
    }

    @Bean
    public Queue paymentDlq() { return QueueBuilder.durable(PAYMENT_DLQ).build(); }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentQueue).to(appExchange).with("order.created");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(messageConverter());
        return t;
    }
}
