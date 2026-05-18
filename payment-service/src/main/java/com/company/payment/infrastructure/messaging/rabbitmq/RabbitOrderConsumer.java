package com.company.payment.infrastructure.messaging.rabbitmq;

import com.company.payment.domain.event.OrderCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RabbitOrderConsumer {
    private final RabbitTemplate rabbitTemplate;
    public static final String EXCHANGE = "payment.exchange";

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("Payment Service processing Order ID: " + event.getOrderId());
        
        // Simulate payment logic
        Map<String, Object> paymentResult = new HashMap<>();
        paymentResult.put("orderId", event.getOrderId());
        paymentResult.put("status", "SUCCESS");
        paymentResult.put("transactionId", "TXN-" + System.currentTimeMillis());

        System.out.println("Payment SUCCESS for Order: " + event.getOrderId());
        
        // Notify others that payment is processed
        rabbitTemplate.convertAndSend(EXCHANGE, "payment.processed", paymentResult);
    }
}
