package com.example.paymentservice.infrastructure.messaging;


import com.example.paymentservice.domain.model.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPaymentCompleted(Payment payment) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PAYMENT_COMPLETED_KEY, payment);
        System.out.println("✅ payment.completed event published for order: " + payment.getOrderId());
    }

    public void publishPaymentFailed(Payment payment) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PAYMENT_FAILED_KEY, payment);
        System.out.println("❌ payment.failed event published for order: " + payment.getOrderId());
    }
}
