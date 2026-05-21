package com.ecommerce.payment.infrastructure.messaging;

import com.ecommerce.payment.application.ports.PaymentEventPublisher;
import com.ecommerce.shared.messaging.event.BaseEvent;
import com.ecommerce.shared.messaging.event.PaymentCompletedEvent;
import com.ecommerce.shared.messaging.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisherAdapter implements PaymentEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "app.exchange";

    @Override
    public void publish(BaseEvent event) {
        String routingKey = getRoutingKey(event);
        rabbitTemplate.convertAndSend(EXCHANGE, routingKey, event);
    }

    private String getRoutingKey(BaseEvent event) {
        if (event instanceof PaymentCompletedEvent) return "payment.completed";
        if (event instanceof PaymentFailedEvent) return "payment.failed";
        return "default.key";
    }
}
