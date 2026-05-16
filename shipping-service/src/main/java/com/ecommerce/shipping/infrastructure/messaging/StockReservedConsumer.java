package com.ecommerce.shipping.infrastructure.messaging;

import com.ecommerce.shipping.application.service.ShippingService;
import com.ecommerce.shipping.domain.event.StockReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockReservedConsumer {

    private final ShippingService shippingService;

    @RabbitListener(queues = "shipping.stock.queue")
    public void handleStockReserved(StockReservedEvent event) {
        log.info("[SHIPPING] Received stock.reserved event for order: {}", event.getOrderId());
        shippingService.handleStockReserved(event);
    }
}
