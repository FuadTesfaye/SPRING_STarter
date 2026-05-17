package com.eventdriven.paymentservice.infrastructure.messaging;

import com.eventdriven.paymentservice.application.dto.PaymentCompletedEvent;
import com.eventdriven.paymentservice.application.dto.PaymentFailedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class RabbitMQSender {
    
    private static final Logger log = LoggerFactory.getLogger(RabbitMQSender.class);
    private final RabbitTemplate rabbitTemplate;
    
    @Value("${app.exchange}")
    private String exchange;
    
    @Value("${app.payment-completed-routing}")
    private String paymentCompletedRouting;
    
    @Value("${app.payment-failed-routing}")
    private String paymentFailedRouting;
    
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    
    public void sendPaymentCompletedEvent(Long orderId, Long paymentId, BigDecimal amount) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(orderId, paymentId, amount, LocalDateTime.now());
        log.info("Sending PaymentCompleted event: {}", event);
        rabbitTemplate.convertAndSend(exchange, paymentCompletedRouting, event);
    }
    
    public void sendPaymentFailedEvent(Long orderId, String reason) {
        PaymentFailedEvent event = new PaymentFailedEvent(orderId, reason, LocalDateTime.now());
        log.info("Sending PaymentFailed event: {}", event);
        rabbitTemplate.convertAndSend(exchange, paymentFailedRouting, event);
    }
}