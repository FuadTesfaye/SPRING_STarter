package com.example.notification.infrastructure.config;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.QueueNames;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EventExchange.APP_EXCHANGE);
    }

    @Bean
    public Queue notificationEventsQueue() {
        return new Queue(QueueNames.NOTIFICATION_EVENTS_QUEUE, true);
    }

    @Bean
    public Binding notificationUserRegisteredBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.USER_REGISTERED);
    }

    @Bean
    public Binding notificationOrderCreatedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.ORDER_CREATED);
    }

    @Bean
    public Binding notificationPaymentCompletedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.PAYMENT_COMPLETED);
    }

    @Bean
    public Binding notificationPaymentFailedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.PAYMENT_FAILED);
    }

    @Bean
    public Binding notificationStockReservedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.STOCK_RESERVED);
    }

    @Bean
    public Binding notificationStockFailedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.STOCK_FAILED);
    }

    @Bean
    public Binding notificationShipmentCreatedBinding(Queue notificationEventsQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(notificationEventsQueue)
                .to(appExchange)
                .with(EventRoutingKeys.SHIPMENT_CREATED);
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
