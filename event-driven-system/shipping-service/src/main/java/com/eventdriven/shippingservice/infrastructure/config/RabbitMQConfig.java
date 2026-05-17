package com.eventdriven.shippingservice.infrastructure.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    
    @Value("${app.exchange}")
    private String exchange;
    
    @Value("${app.payment-completed-routing}")
    private String paymentCompletedRouting;
    
    @Value("${app.stock-reserved-routing}")
    private String stockReservedRouting;
    
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(exchange);
    }
    
    @Bean
    public Queue paymentCompletedQueue() {
        return new Queue("shipping.payment.completed.queue", true);
    }
    
    @Bean
    public Queue stockReservedQueue() {
        return new Queue("shipping.stock.reserved.queue", true);
    }
    
    @Bean
    public Binding paymentCompletedBinding() {
        return BindingBuilder.bind(paymentCompletedQueue()).to(exchange()).with(paymentCompletedRouting);
    }
    
    @Bean
    public Binding stockReservedBinding() {
        return BindingBuilder.bind(stockReservedQueue()).to(exchange()).with(stockReservedRouting);
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