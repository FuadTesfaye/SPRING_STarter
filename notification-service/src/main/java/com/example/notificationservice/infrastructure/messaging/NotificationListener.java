package com.example.notificationservice.infrastructure.messaging;



import com.example.notificationservice.application.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private final NotificationService notificationService;

    public NotificationListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.NOTIFICATION_QUEUE)
    public void handleAllEvents(Object event) {
        String eventType = getEventType(event);
        notificationService.notifyUser(event, eventType);
    }

    private String getEventType(Object event) {
        String className = event.getClass().getSimpleName();
        return switch (className) {
            case "Order" -> "order.created";
            case "Payment" -> "payment.completed or payment.failed";
            case "Stock" -> "stock.reserved or stock.failed";
            case "Shipment" -> "shipment.created";
            default -> "unknown.event";
        };
    }
}
