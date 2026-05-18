package com.company.beautyprofile.infrastructure.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.Map;

@Component
public class ProfileEventListener {

    @RabbitListener(queues = "order.completed.profile.q")
    public void handleOrderCompleted(Map<String, Object> event) {
        System.out.println("Updating beauty profile history with new purchase...");
    }
}
