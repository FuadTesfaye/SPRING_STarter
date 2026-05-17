package com.example.task.shipping.listener;

import com.example.task.shipping.model.Shipment;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class ShippingEventListener {

    private static boolean isPaid = false;
    private static boolean isStockReserved = false;

    @RabbitListener(queues = "notificationQueue")
    public void handleFulfillmentEvents(String message) {
        if (message.startsWith("PAYMENT_COMPLETED:")) {
            isPaid = true;
        } else if (message.startsWith("STOCK_RESERVED:")) {
            isStockReserved = true;
        }

        if (isPaid && isStockReserved) {
            String[] parts = message.split(":");
            String orderId = parts.length > 1 ? parts[1] : "UNKNOWN";

            String shipmentId = UUID.randomUUID().toString();
            String trackingNum = "TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Shipment shipment = new Shipment(shipmentId, orderId, trackingNum);
            shipment.ship();

            System.out.println("[SHIPPING SYSTEM] Order fulfilled! Tracking Number created: " + shipment.getTrackingNumber());

            // Reset flags for the next order simulation
            isPaid = false;
            isStockReserved = false;
        }
    }
}
