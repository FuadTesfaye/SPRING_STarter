package com.example.inventory.infrastructure.messaging;

import com.example.events.EventExchange;
import com.example.events.EventRoutingKeys;
import com.example.events.StockFailedEvent;
import com.example.events.StockReservedEvent;
import com.example.inventory.application.port.out.InventoryEventPublisher;
import com.example.inventory.domain.model.Reservation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class InventoryEventPublisherAdapter implements InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public InventoryEventPublisherAdapter(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishStockReserved(Reservation reservation) {
        StockReservedEvent event = new StockReservedEvent(
                reservation.getOrderId(),
                reservation.getProductId(),
                reservation.getQuantity(),
                reservation.getCreatedAt()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.STOCK_RESERVED, event);
    }

    @Override
    public void publishStockFailed(Long orderId, String failureReason) {
        StockFailedEvent event = new StockFailedEvent(
                orderId,
                0L,
                0,
                failureReason,
                java.time.Instant.now()
        );

        rabbitTemplate.convertAndSend(EventExchange.APP_EXCHANGE, EventRoutingKeys.STOCK_FAILED, event);
    }
}
