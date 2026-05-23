package com.ticketbooking.notification.infrastructure.config;

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

    public static final String EXCHANGE           = "app.exchange";
    public static final String DLX                = "app.dlx";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String NOTIFICATION_DLQ   = "notification.dlq";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }
    @Bean public DirectExchange deadLetterExchange() { return new DirectExchange(DLX); }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", NOTIFICATION_DLQ)
            .build();
    }

    @Bean public Queue notificationDeadLetterQueue() { return new Queue(NOTIFICATION_DLQ, true); }

    @Bean
    public Binding notificationDlqBinding(Queue notificationDeadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(notificationDeadLetterQueue).to(deadLetterExchange).with(NOTIFICATION_DLQ);
    }

    @Bean public Binding bindUserRegistered(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("user.registered");
    }
    @Bean public Binding bindOrderCreated(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("order.created");
    }
    @Bean public Binding bindPaymentCompleted(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("payment.completed");
    }
    @Bean public Binding bindPaymentFailed(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("payment.failed");
    }
    @Bean public Binding bindStockReserved(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("stock.reserved");
    }
    @Bean public Binding bindStockFailed(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("stock.failed");
    }
    @Bean public Binding bindShipmentCreated(Queue notificationQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationQueue).to(appExchange).with("shipment.created");
    }

    @Bean public Jackson2JsonMessageConverter messageConverter() { ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()); return new Jackson2JsonMessageConverter(mapper); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(messageConverter());
        return t;
    }
}
