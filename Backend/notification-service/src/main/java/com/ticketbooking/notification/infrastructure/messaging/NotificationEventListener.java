package com.ticketbooking.notification.infrastructure.messaging;

import com.ticketbooking.notification.application.usecase.ProcessNotificationUseCase;
import com.ticketbooking.notification.infrastructure.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationEventListener {

    private final ProcessNotificationUseCase useCase;

    public NotificationEventListener(ProcessNotificationUseCase useCase) {
        this.useCase = useCase;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleEvent(Map<String, Object> event,
                            @Header(value = "amqp_receivedRoutingKey", required = false) String routingKey) {
        useCase.execute(routingKey != null ? routingKey : "unknown", event);
    }
}
