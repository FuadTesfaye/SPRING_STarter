package com.company.product.infrastructure.messaging;

import com.company.product.domain.event.ProductViewedEvent;
import com.company.product.domain.event.ShadeSelectedEvent;
import com.company.product.domain.event.WishlistAddedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE = "beauty.events";

    public ProductEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductViewed(String productId, String userId, String category) {
        ProductViewedEvent event = new ProductViewedEvent(productId, userId, category);
        rabbitTemplate.convertAndSend(EXCHANGE, "product.viewed", event);
    }

    public void publishShadeSelected(String productId, String shadeCode, String userId) {
        ShadeSelectedEvent event = new ShadeSelectedEvent(productId, shadeCode, userId);
        rabbitTemplate.convertAndSend(EXCHANGE, "shade.selected", event);
    }

    public void publishWishlistAdded(String productId, String userId) {
        WishlistAddedEvent event = new WishlistAddedEvent(productId, userId);
        rabbitTemplate.convertAndSend(EXCHANGE, "wishlist.added", event);
    }
}
