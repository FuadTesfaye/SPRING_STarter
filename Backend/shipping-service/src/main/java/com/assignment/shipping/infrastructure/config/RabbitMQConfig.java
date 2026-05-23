package com.assignment.shipping.infrastructure.config;

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

    public static final String EXCHANGE       = "app.exchange";
    public static final String DLX            = "app.dlx";
    public static final String SHIPPING_QUEUE = "shipping.queue";
    public static final String SHIPPING_DLQ   = "shipping.dlq";

    @Bean public TopicExchange appExchange() { return new TopicExchange(EXCHANGE); }
    @Bean public DirectExchange deadLetterExchange() { return new DirectExchange(DLX); }

    @Bean
    public Queue shippingQueue() {
        return QueueBuilder.durable(SHIPPING_QUEUE)
            .withArgument("x-dead-letter-exchange", DLX)
            .withArgument("x-dead-letter-routing-key", SHIPPING_DLQ)
            .build();
    }

    @Bean public Queue shippingDeadLetterQueue() { return new Queue(SHIPPING_DLQ, true); }

    @Bean
    public Binding shippingDlqBinding(Queue shippingDeadLetterQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(shippingDeadLetterQueue).to(deadLetterExchange).with(SHIPPING_DLQ);
    }

    @Bean
    public Binding shippingPaymentBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue).to(appExchange).with("payment.completed");
    }

    @Bean
    public Binding shippingStockBinding(Queue shippingQueue, TopicExchange appExchange) {
        return BindingBuilder.bind(shippingQueue).to(appExchange).with("stock.reserved");
    }

    @Bean public Jackson2JsonMessageConverter messageConverter() { ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule()); return new Jackson2JsonMessageConverter(mapper); }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf) {
        RabbitTemplate t = new RabbitTemplate(cf);
        t.setMessageConverter(messageConverter());
        return t;
    }
}
