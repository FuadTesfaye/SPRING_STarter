package com.example.payment.infrastructure.messaging.inbound;

import com.example.payment.application.dto.OrderPaymentCommand;
import com.example.payment.application.service.PaymentForOrderApplicationService;
import com.example.payment.infrastructure.configuration.RabbitMQConfig;
import com.example.payment.infrastructure.messaging.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Inbound messaging adapter — translates wire format and delegates to application service.
 */
@Component
@RequiredArgsConstructor
public class OrderCreatedMqListener {

    private final PaymentForOrderApplicationService paymentForOrder;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_CREATED)
    public void onOrderCreated(OrderCreatedEvent event) {
        paymentForOrder.settlePaymentForOrder(new OrderPaymentCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity(),
                event.getTotalAmount()
        ));
    }
}
