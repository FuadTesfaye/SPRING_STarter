package com.app.payment.infrastructure.messaging;

import com.app.payment.application.usecases.ProcessPaymentUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private final ProcessPaymentUseCase processPaymentUseCase;

    public OrderCreatedListener(ProcessPaymentUseCase processPaymentUseCase) {
        this.processPaymentUseCase = processPaymentUseCase;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void onOrderCreated(OrderCreatedEvent event) {
        processPaymentUseCase.execute(event.orderId(), event.amount());
    }
}
