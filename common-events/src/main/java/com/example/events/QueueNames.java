package com.example.events;

public final class QueueNames {

    public static final String PAYMENT_ORDER_CREATED_QUEUE = "payment.order-created.queue";
    public static final String INVENTORY_ORDER_CREATED_QUEUE = "inventory.order-created.queue";
    public static final String SHIPPING_EVENTS_QUEUE = "shipping.events.queue";
    public static final String NOTIFICATION_EVENTS_QUEUE = "notification.events.queue";

    private QueueNames() {
    }
}
