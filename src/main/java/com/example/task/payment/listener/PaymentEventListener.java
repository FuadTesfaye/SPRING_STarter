package com.example.task.payment.listener;

import com.example.task.payment.model.Transaction;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class PaymentEventListener {

    private final RabbitTemplate rabbitTemplate;

    public PaymentEventListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "notificationQueue")
    public void handleOrderCreatedEvent(String message) {
        if (message.startsWith("ORDER_CREATED:")) {
            String[] parts = message.split(":");
            String orderId = parts[1];
            double amount = Double.parseDouble(parts[2]);

            String transactionId = UUID.randomUUID().toString();
            Transaction transaction = new Transaction(transactionId, orderId, amount);

            transaction.approve();

            System.out.println("[PAYMENT SYSTEM] Successfully processed payment for order: " + orderId);

            String updatePayload = "PAYMENT_COMPLETED:" + orderId;
            rabbitTemplate.convertAndSend("notificationQueue", updatePayload);
        }
    }
}
