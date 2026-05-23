package com.school.auth.infrastructure.config;

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

    public static final String AUTH_QUEUE = "auth.queue";
    public static final String AUTH_DLQ = "auth.queue.dlq";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean
    public Queue authQueue() {
        return QueueBuilder.durable(AUTH_QUEUE)
            .withArgument("x-dead-letter-exchange", exchange + ".dlx")
            .withArgument("x-dead-letter-routing-key", "dlq.auth")
            .build();
    }

    @Bean
    public Queue authDeadLetterQueue() {
        return QueueBuilder.durable(AUTH_DLQ).build();
    }

    @Bean
    public Binding authBinding(Queue authQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(authQueue).to(appExchange).with("user.registered");
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
