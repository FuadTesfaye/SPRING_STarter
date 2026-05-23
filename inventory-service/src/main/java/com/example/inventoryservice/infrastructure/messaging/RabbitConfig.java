package com.example.inventoryservice.infrastructure.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class RabbitConfig {

    @Value("${app.exchange}")
    private String exchangeName;

    public static final String DLX = "app.exchange.dlx";
    public static final String DLQ = "inventory.queue.dlq";

    @Bean
    TopicExchange topicExchange() { return new TopicExchange(exchangeName, true, false); }

    @Bean
    TopicExchange deadLetterExchange() { return new TopicExchange(DLX, true, false); }

    @Bean
    Queue deadLetterQueue() { return QueueBuilder.durable(DLQ).build(); }

    @Bean
    Binding dlqBinding(Queue deadLetterQueue, TopicExchange deadLetterExchange) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with("#");
    }

    @Bean
    Queue serviceQueue() {
        return QueueBuilder.durable("inventory.queue")
            .withArgument("x-dead-letter-exchange", DLX)
            .build();
    }

@Bean
Binding binding0(Queue serviceQueue, TopicExchange topicExchange) {
    return BindingBuilder.bind(serviceQueue).to(topicExchange).with("order.created");
}

    @Bean
    MessageConverter jsonMessageConverter() { return new Jackson2JsonMessageConverter(); }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory cf, MessageConverter mc) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(mc);
        return t;
    }
}
