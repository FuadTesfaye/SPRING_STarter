package com.example.payment.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Dead Letter Queue Configuration for Payment Service
 * 
 * When a message fails processing (after retries), it's sent to DLQ for manual inspection.
 */
@Configuration
public class DeadLetterQueueConfig {

    // DLQ Exchange and Queue
    public static final String DLQ_EXCHANGE = "payment.dlq.exchange";
    public static final String DLQ_QUEUE = "payment.dlq.queue";
    public static final String DLQ_ROUTING_KEY = "payment.dlq";

    // Main queue configuration with DLQ reference
    public static final String PAYMENT_ORDER_CREATED_QUEUE = "payment.order-created.queue";
    public static final String PAYMENT_ORDER_CREATED_DLX = "payment.order-created.dlx";

    /**
     * Dead Letter Exchange - receives messages that failed processing
     */
    @Bean
    public DirectExchange paymentDlqExchange() {
        return new DirectExchange(DLQ_EXCHANGE, true, false);
    }

    /**
     * Dead Letter Queue - stores failed messages for inspection
     */
    @Bean
    public Queue paymentDlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE)
                .build();
    }

    /**
     * Binding between DLQ Exchange and DLQ Queue
     */
    @Bean
    public Binding paymentDlqBinding(DirectExchange paymentDlqExchange) {
        return BindingBuilder.bind(paymentDlqQueue())
                .to(paymentDlqExchange)
                .with(DLQ_ROUTING_KEY);
    }

    /**
     * Main queue with DLQ configuration
     * When message fails after retries, it's sent to DLQ
     */
    @Bean
    public Queue paymentOrderCreatedQueueWithDlq() {
        return QueueBuilder.durable(PAYMENT_ORDER_CREATED_QUEUE)
                .deadLetterExchange(DLQ_EXCHANGE)
                .deadLetterRoutingKey(DLQ_ROUTING_KEY)
                .build();
    }
}
