package com.ecommerce.shipping.infrastructure.messaging;

import com.ecommerce.shipping.application.service.ShippingService;
import com.ecommerce.shipping.domain.event.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedConsumer {

    private final ShippingService shippingService;

    @RabbitListener(queues = "shipping.payment.queue")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("[SHIPPING] Received payment.completed event for order: {}", event.getOrderId());
        shippingService.handlePaymentCompleted(event);
    }
}
