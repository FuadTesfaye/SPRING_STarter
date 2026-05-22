package com.ecommerce.shipping.infrastructure.messaging.listener;

import com.ecommerce.shared.messaging.event.StockReservedEvent;
import com.ecommerce.shipping.application.service.ShippingApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockReservedEventListener {

    private final ShippingApplicationService shippingService;

    @RabbitListener(queues = "shipping.stock_reserved.queue")
    public void onStockReserved(StockReservedEvent event) {
        log.info("Received StockReservedEvent in Shipping: {}", event);
        shippingService.handleStockReserved(event.getOrderId());
    }
}
