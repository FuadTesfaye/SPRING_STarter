package com.ecommerce.notification.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }

    // One queue per event type for type-safe deserialization
    @Bean public Queue notifUserQueue() { return QueueBuilder.durable("notification.user.queue").build(); }
    @Bean public Queue notifOrderQueue() { return QueueBuilder.durable("notification.order.queue").build(); }
    @Bean public Queue notifPayCompletedQueue() { return QueueBuilder.durable("notification.payment.completed.queue").build(); }
    @Bean public Queue notifPayFailedQueue() { return QueueBuilder.durable("notification.payment.failed.queue").build(); }
    @Bean public Queue notifStockReservedQueue() { return QueueBuilder.durable("notification.stock.reserved.queue").build(); }
    @Bean public Queue notifStockFailedQueue() { return QueueBuilder.durable("notification.stock.failed.queue").build(); }
    @Bean public Queue notifShipmentQueue() { return QueueBuilder.durable("notification.shipment.queue").build(); }

    @Bean public Binding bindUser(Queue notifUserQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifUserQueue).to(appExchange).with("user.registered");
    }
    @Bean public Binding bindOrder(Queue notifOrderQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifOrderQueue).to(appExchange).with("order.created");
    }
    @Bean public Binding bindPayCompleted(Queue notifPayCompletedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifPayCompletedQueue).to(appExchange).with("payment.completed");
    }
    @Bean public Binding bindPayFailed(Queue notifPayFailedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifPayFailedQueue).to(appExchange).with("payment.failed");
    }
    @Bean public Binding bindStockReserved(Queue notifStockReservedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifStockReservedQueue).to(appExchange).with("stock.reserved");
    }
    @Bean public Binding bindStockFailed(Queue notifStockFailedQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifStockFailedQueue).to(appExchange).with("stock.failed");
    }
    @Bean public Binding bindShipment(Queue notifShipmentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notifShipmentQueue).to(appExchange).with("shipment.created");
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
