package com.example.payment.infrastructure.messaging;

import com.example.events.OrderCreatedEvent;
import com.example.events.QueueNames;
import com.example.payment.application.service.PaymentApplicationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final PaymentApplicationService paymentApplicationService;

    public OrderCreatedListener(PaymentApplicationService paymentApplicationService) {
        this.paymentApplicationService = paymentApplicationService;
    }

    @RabbitListener(queues = QueueNames.PAYMENT_ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        paymentApplicationService.processPayment(event);
    }
}
