package com.example.payment.infrastructure.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

/**
 * Retry Configuration for Payment Service
 * 
 * Implements exponential backoff retry strategy:
 * - Retry up to 3 times
 * - Initial delay: 1 second
 * - Max delay: 10 seconds
 * - Multiplier: 2.0 (doubles each time)
 * 
 * Retry sequence:
 * 1st attempt: immediate
 * 2nd attempt: 1 second delay
 * 3rd attempt: 2 seconds delay
 * 4th attempt: 4 seconds delay
 * After 4 attempts: Send to Dead Letter Queue
 */
@Configuration
public class RetryConfig {

    /**
     * Retry template with exponential backoff
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        // Retry policy: max 3 retries
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(4); // 1 initial + 3 retries
        retryTemplate.setRetryPolicy(retryPolicy);

        // Backoff policy: exponential with initial delay 1s, max 10s, multiplier 2.0
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000); // 1 second
        backOffPolicy.setMaxInterval(10000); // 10 seconds
        backOffPolicy.setMultiplier(2.0); // Double each time
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }

    /**
     * Configure RabbitMQ listener container with retry and DLQ
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            RetryTemplate retryTemplate) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);

        // Enable retry
        factory.setRetryTemplate(retryTemplate);

        // Message converter
        factory.setMessageConverter(new Jackson2JsonMessageConverter());

        return factory;
    }
}
