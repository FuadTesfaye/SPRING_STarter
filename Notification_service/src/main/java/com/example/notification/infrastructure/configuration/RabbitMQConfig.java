package com.example.notification.infrastructure.configuration;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "app.exchange";
    public static final String QUEUE_NOTIFICATIONS = "notification.events";

    @Bean
    public TopicExchange appExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Declarables notificationBindings(TopicExchange appExchange) {
        Queue q = new Queue(QUEUE_NOTIFICATIONS, true);
        return new Declarables(
                q,
                BindingBuilder.bind(q).to(appExchange).with("user.registered"),
                BindingBuilder.bind(q).to(appExchange).with("order.created"),
                BindingBuilder.bind(q).to(appExchange).with("payment.completed"),
                BindingBuilder.bind(q).to(appExchange).with("payment.failed"),
                BindingBuilder.bind(q).to(appExchange).with("stock.reserved"),
                BindingBuilder.bind(q).to(appExchange).with("stock.failed"),
                BindingBuilder.bind(q).to(appExchange).with("shipment.created")
        );
    }

    @Bean(name = "rawListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory rawListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new SimpleMessageConverter());
        return factory;
    }
}
