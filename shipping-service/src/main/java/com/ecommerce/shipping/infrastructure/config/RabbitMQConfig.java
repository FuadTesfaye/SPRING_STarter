package com.ecommerce.shipping.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String SHIPPING_PAYMENT_QUEUE = "shipping.payment.queue";
    public static final String SHIPPING_STOCK_QUEUE = "shipping.stock.queue";
    public static final String SHIPPING_DLQ = "shipping.dlq";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }

    @Bean
    public Queue shippingPaymentQueue() {
        return QueueBuilder.durable(SHIPPING_PAYMENT_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", SHIPPING_DLQ)
                .build();
    }

    @Bean
    public Queue shippingStockQueue() {
        return QueueBuilder.durable(SHIPPING_STOCK_QUEUE)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", SHIPPING_DLQ)
                .build();
    }

    @Bean
    public Queue shippingDlq() { return QueueBuilder.durable(SHIPPING_DLQ).build(); }

    @Bean
    public Binding shippingPaymentBinding(Queue shippingPaymentQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingPaymentQueue).to(appExchange).with("payment.completed");
    }

    @Bean
    public Binding shippingStockBinding(Queue shippingStockQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingStockQueue).to(appExchange).with("stock.reserved");
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
