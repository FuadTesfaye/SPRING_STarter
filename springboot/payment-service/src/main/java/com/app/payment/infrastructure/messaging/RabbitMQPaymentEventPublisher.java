package com.app.payment.infrastructure.messaging;

import com.app.payment.application.ports.PaymentEventPublisher;
import com.app.payment.domain.Payment;
import com.app.payment.domain.PaymentCompleted;
import com.app.payment.domain.PaymentFailed;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPaymentEventPublisher implements PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQPaymentEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishPaymentCompleted(Payment payment) {
        PaymentCompleted event = new PaymentCompleted(payment.getId(), payment.getOrderId(), payment.getAmount());
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PAYMENT_COMPLETED_ROUTING_KEY, event);
    }

    @Override
    public void publishPaymentFailed(Payment payment) {
        PaymentFailed event = new PaymentFailed(payment.getOrderId(), "Payment failed simulation");
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.PAYMENT_FAILED_ROUTING_KEY, event);
    }
}
