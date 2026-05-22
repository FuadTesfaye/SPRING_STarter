package com.ecommerce.payment.application.ports;

import com.ecommerce.shared.messaging.event.BaseEvent;

public interface EventPublisher {
    void publish(BaseEvent event);
}
