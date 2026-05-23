package com.school.notification.infrastructure.config;

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

    public static final String NOTIF_USER_QUEUE              = "notification.user.queue";
    public static final String NOTIF_ORDER_QUEUE             = "notification.order.queue";
    public static final String NOTIF_PAYMENT_COMPLETED_QUEUE = "notification.payment.completed.queue";
    public static final String NOTIF_PAYMENT_FAILED_QUEUE    = "notification.payment.failed.queue";
    public static final String NOTIF_STOCK_RESERVED_QUEUE    = "notification.stock.reserved.queue";
    public static final String NOTIF_STOCK_FAILED_QUEUE      = "notification.stock.failed.queue";
    public static final String NOTIF_SHIPMENT_QUEUE          = "notification.shipment.queue";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(exchange, true, false);
    }

    @Bean public Queue notifUserQueue()             { return QueueBuilder.durable(NOTIF_USER_QUEUE).build(); }
    @Bean public Queue notifOrderQueue()            { return QueueBuilder.durable(NOTIF_ORDER_QUEUE).build(); }
    @Bean public Queue notifPaymentCompletedQueue() { return QueueBuilder.durable(NOTIF_PAYMENT_COMPLETED_QUEUE).build(); }
    @Bean public Queue notifPaymentFailedQueue()    { return QueueBuilder.durable(NOTIF_PAYMENT_FAILED_QUEUE).build(); }
    @Bean public Queue notifStockReservedQueue()    { return QueueBuilder.durable(NOTIF_STOCK_RESERVED_QUEUE).build(); }
    @Bean public Queue notifStockFailedQueue()      { return QueueBuilder.durable(NOTIF_STOCK_FAILED_QUEUE).build(); }
    @Bean public Queue notifShipmentQueue()         { return QueueBuilder.durable(NOTIF_SHIPMENT_QUEUE).build(); }

    @Bean
    public Binding bindUserRegistered(TopicExchange appExchange) {
        return BindingBuilder.bind(notifUserQueue()).to(appExchange).with("user.registered");
    }

    @Bean
    public Binding bindOrderCreated(TopicExchange appExchange) {
        return BindingBuilder.bind(notifOrderQueue()).to(appExchange).with("order.created");
    }

    @Bean
    public Binding bindPaymentCompleted(TopicExchange appExchange) {
        return BindingBuilder.bind(notifPaymentCompletedQueue()).to(appExchange).with("payment.completed");
    }

    @Bean
    public Binding bindPaymentFailed(TopicExchange appExchange) {
        return BindingBuilder.bind(notifPaymentFailedQueue()).to(appExchange).with("payment.failed");
    }

    @Bean
    public Binding bindStockReserved(TopicExchange appExchange) {
        return BindingBuilder.bind(notifStockReservedQueue()).to(appExchange).with("stock.reserved");
    }

    @Bean
    public Binding bindStockFailed(TopicExchange appExchange) {
        return BindingBuilder.bind(notifStockFailedQueue()).to(appExchange).with("stock.failed");
    }

    @Bean
    public Binding bindShipmentCreated(TopicExchange appExchange) {
        return BindingBuilder.bind(notifShipmentQueue()).to(appExchange).with("shipment.created");
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
