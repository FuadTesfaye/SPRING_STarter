package com.eventdriven.notificationservice.infrastructure.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(NotificationEventListener.class);
    
    @RabbitListener(queues = "notification.all.queue")
    public void handleAllEvents(@Payload Object event) {
        log.info("========================================");
        log.info("NOTIFICATION RECEIVED");
        log.info("Event Type: {}", event.getClass().getSimpleName());
        log.info("Event Data: {}", event);
        log.info("========================================");
    }
}