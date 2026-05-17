package com.microservices.payment.infrastructure.messaging;

import com.microservices.payment.domain.event.PaymentCompletedEvent;
import com.microservices.payment.domain.event.PaymentEventPublisher;
import com.microservices.payment.domain.event.PaymentFailedEvent;
import com.microservices.payment.infrastructure.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPaymentEventPublisher implements PaymentEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQPaymentEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    
    public RabbitMQPaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    @Override
    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Publishing payment completed: {}", event.getPaymentId());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.PAYMENT_COMPLETED_KEY,
            event
        );
    }
    
    @Override
    public void publishPaymentFailed(PaymentFailedEvent event) {
        log.info("Publishing payment failed: {}", event.getPaymentId());
        rabbitTemplate.convertAndSend(
            RabbitMQConfig.EXCHANGE_NAME,
            RabbitMQConfig.PAYMENT_FAILED_KEY,
            event
        );
    }
}
