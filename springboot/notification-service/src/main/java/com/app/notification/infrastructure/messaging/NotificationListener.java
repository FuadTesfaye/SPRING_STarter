package com.app.notification.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void onMessage(Object payload, Message message) {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        logger.info("[NOTIFICATION] Received event with routing key: {}", routingKey);
        logger.info("[NOTIFICATION] Payload: {}", payload);
    }
}
