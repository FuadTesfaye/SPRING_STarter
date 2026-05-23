package com.assignment.inventory.infrastructure.config;

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

    public static final String EXCHANGE        = "app.exchange";
    public static final String DLX             = "app.dlx";
    public static final String INVENTORY_QUEUE = "inventory.queue";
    public static final String INVENTORY_DLQ   = "inventory.dlq";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }
    @Bean public DirectExchange deadLetterExchange() { return new DirectExchange(DLX); }

    @Bean
    public Queue inventoryQueue() {
        return QueueBuilder.durable(INVENTORY_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", INVENTORY_DLQ)
            .build();
    }

    @Bean public Queue inventoryDeadLetterQueue() { return new Queue(INVENTORY_DLQ, true); }

    @Bean
    public Binding inventoryDlqBinding(Queue inventoryDeadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(inventoryDeadLetterQueue).to(deadLetterExchange).with(INVENTORY_DLQ);
    }

    @Bean
    public Binding inventoryBinding(Queue inventoryQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(inventoryQueue).to(appExchange).with("order.created");
    }

    @Bean public Jackson2JsonMessageConverter messageConverter() { ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()); return new Jackson2JsonMessageConverter(mapper); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(messageConverter());
        return t;
    }
}
