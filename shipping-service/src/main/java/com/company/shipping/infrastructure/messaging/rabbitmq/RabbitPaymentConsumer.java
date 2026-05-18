package com.company.shipping.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class RabbitPaymentConsumer {

    @RabbitListener(queues = "shipping.payment.queue")
    public void handlePaymentProcessed(Map<String, Object> message) {
        System.out.println("Shipping Service RECEIVED Payment Confirmation for Order: " + message.get("orderId"));
        System.out.println("Initiating Shipping for Transaction: " + message.get("transactionId"));
    }
}
