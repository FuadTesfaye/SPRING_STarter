package com.ecommerce.shipping.infrastructure.messaging.listener;

import com.ecommerce.shared.messaging.event.PaymentCompletedEvent;
import com.ecommerce.shipping.application.service.ShippingApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCompletedEventListener {

    private final ShippingApplicationService shippingService;

    @RabbitListener(queues = "shipping.payment_completed.queue")
    public void onPaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received PaymentCompletedEvent in Shipping: {}", event);
        shippingService.handlePaymentCompleted(event.getOrderId());
    }
}
