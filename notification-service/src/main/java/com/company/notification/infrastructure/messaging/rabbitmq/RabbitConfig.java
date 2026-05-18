package com.company.notification.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String AUTH_EXCHANGE = "auth.exchange";
    public static final String ORDER_EXCHANGE = "order.exchange";
    public static final String QUEUE = "notification.queue";

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue productRestockedQueue() { return new Queue("product.restocked.queue"); }
    
    @Bean
    public Queue shadeRestockQueue() { return new Queue("shade.restock.q"); }
    
    @Bean
    public Queue loyaltyEarnedQueue() { return new Queue("loyalty.earned.q"); }
    
    @Bean
    public Queue tierUpgradedQueue() { return new Queue("tier.upgraded.q"); }
    
    @Bean
    public Queue beautyBoxShippedQueue() { return new Queue("beauty.box.shipped.queue"); }
    
    @Bean
    public Queue newArrivalQueue() { return new Queue("new.arrival.queue"); }

    @Bean
    public TopicExchange authExchange() {
        return new TopicExchange(AUTH_EXCHANGE);
    }

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE);
    }

    @Bean
    public Binding authBinding(Queue notificationQueue, TopicExchange authExchange) {
        return BindingBuilder.bind(notificationQueue).to(authExchange).with("user.#");
    }

    @Bean
    public Binding orderBinding(Queue notificationQueue, TopicExchange orderExchange) {
        return BindingBuilder.bind(notificationQueue).to(orderExchange).with("order.#");
    }

    @Bean
    public org.springframework.amqp.support.converter.MessageConverter jsonMessageConverter() {
        return new org.springframework.amqp.support.converter.Jackson2JsonMessageConverter();
    }
}
