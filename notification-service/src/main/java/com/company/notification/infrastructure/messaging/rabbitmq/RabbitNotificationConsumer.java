package com.company.notification.infrastructure.messaging.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitNotificationConsumer {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void handleNotification(Map<String, Object> message) {
        System.out.println("RECEIVED NOTIFICATION MESSAGE: " + message);
        
        if (message.containsKey("username")) {
            System.out.println("Sending Welcome Email to: " + message.get("username"));
        } else if (message.containsKey("orderId")) {
            System.out.println("Sending Order Confirmation for ID: " + message.get("orderId"));
        }
    }
}
