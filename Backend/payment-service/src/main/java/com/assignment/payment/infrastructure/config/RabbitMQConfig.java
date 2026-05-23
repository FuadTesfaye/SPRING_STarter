package com.assignment.payment.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE     = "app.exchange";
    public static final String DLX          = "app.dlx";
    public static final String PAYMENT_QUEUE = "payment.queue";
    public static final String PAYMENT_DLQ   = "payment.dlq";

    @Bean
    public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public DirectExchange deadLetterExchange() { return new DirectExchange(DLX); }

    @Bean
    public Queue paymentQueue() {
        return QueueBuilder.durable(PAYMENT_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", PAYMENT_DLQ)
            .build();
    }

    @Bean
    public Queue paymentDeadLetterQueue() { return new Queue(PAYMENT_DLQ, true); }

    @Bean
    public Binding paymentDlqBinding(Queue paymentDeadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(paymentDeadLetterQueue).to(deadLetterExchange).with(PAYMENT_DLQ);
    }

    @Bean
    public Binding paymentBinding(Queue paymentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(paymentQueue).to(appExchange).with("order.created");
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() { ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()); return new Jackson2JsonMessageConverter(mapper); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(messageConverter());
        return t;
    }
}
