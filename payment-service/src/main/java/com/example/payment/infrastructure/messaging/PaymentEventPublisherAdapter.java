package com.example.payment.infrastructure.messaging;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.PaymentCompletedEvent;
import com.example.events.PaymentFailedEvent;
import com.example.payment.application.port.out.PaymentEventPublisher;
import com.example.payment.domain.model.Payment;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventPublisherAdapter implements PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public PaymentEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishPaymentCompleted(Payment payment) {
        PaymentCompletedEvent event = new PaymentCompletedEvent(
                payment.getOrderId(),
                payment.getReference(),
                payment.getAmount(),
                payment.getProcessedAt()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.PAYMENT_COMPLETED, event);
    }

    @Override
    public void publishPaymentFailed(Payment payment) {
        PaymentFailedEvent event = new PaymentFailedEvent(
                payment.getOrderId(),
                payment.getFailureReason(),
                payment.getProcessedAt()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.PAYMENT_FAILED, event);
    }
}
