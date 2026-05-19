package com.example.paymentservice.messaging;

import com.example.paymentservice.config.RabbitMQConfig;
import com.example.paymentservice.dto.OrderEvent;
import com.example.paymentservice.dto.PaymentEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private final RabbitTemplate rabbitTemplate;

    public PaymentListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "order.queue")
    public void processOrder(OrderEvent order) {

        System.out.println("Received order: " + order.getOrderId());

        boolean success = order.getPrice() < 1000;

        if (success) {
            PaymentEvent event =
                    new PaymentEvent(order.getOrderId(), "COMPLETED");

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    "payment.completed",
                    event
            );

            System.out.println("PAYMENT SUCCESS");
        } else {
            PaymentEvent event =
                    new PaymentEvent(order.getOrderId(), "FAILED");

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    "payment.failed",
                    event
            );

            System.out.println("PAYMENT FAILED");
        }
    }
}