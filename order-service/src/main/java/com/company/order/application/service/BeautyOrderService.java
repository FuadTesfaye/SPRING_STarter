package com.company.order.application.service;

import com.company.order.application.dto.BeautyOrderRequest;
import com.company.order.application.dto.BeautyOrderResponse;
import com.company.order.application.dto.GiftWrapRequest;
import com.company.order.domain.event.BeautyOrderCreatedEvent;
import com.company.order.domain.event.GiftOrderCreatedEvent;
import com.company.order.domain.model.BeautyOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BeautyOrderService {

    private final RabbitTemplate rabbitTemplate;
    // We would inject BeautyOrderRepository here, but for brevity we'll simulate it

    public BeautyOrderService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public BeautyOrderResponse createBeautyOrder(BeautyOrderRequest request, String userId) {
        BeautyOrder order = new BeautyOrder();
        order.setId(UUID.randomUUID().toString());
        order.setOriginalOrderId(request.getOriginalOrderId());
        order.setGiftWrapping(request.getGiftWrapping());
        order.setGiftMessage(request.getGiftMessage());
        order.setSample(request.isSample());
        order.setBeautyProfileId(request.getBeautyProfileId());
        order.setShadeSelections(request.getShadeSelections());
        order.setSubscriptionType(request.getSubscriptionType());
        
        // Save to DB here (simulated)

        BeautyOrderCreatedEvent event = new BeautyOrderCreatedEvent(
            order.getId(), 
            userId, 
            order.getGiftWrapping(), 
            order.isSample()
        );
        rabbitTemplate.convertAndSend("beauty.events", "beauty.order.created", event);

        if (order.getGiftWrapping() == BeautyOrder.GiftWrapping.PREMIUM) {
            GiftOrderCreatedEvent giftEvent = new GiftOrderCreatedEvent(order.getId(), order.getGiftMessage());
            rabbitTemplate.convertAndSend("beauty.events", "gift.order.created", giftEvent);
        }

        return mapToResponse(order);
    }

    public BeautyOrderResponse addGiftWrapping(String orderId, GiftWrapRequest request) {
        // Fetch order from DB (simulated)
        BeautyOrder order = new BeautyOrder();
        order.setId(orderId);
        order.setGiftWrapping(request.getGiftWrapping());
        order.setGiftMessage(request.getGiftMessage());

        if (request.getGiftWrapping() == BeautyOrder.GiftWrapping.PREMIUM) {
            GiftOrderCreatedEvent giftEvent = new GiftOrderCreatedEvent(order.getId(), order.getGiftMessage());
            rabbitTemplate.convertAndSend("beauty.events", "gift.order.created", giftEvent);
        }

        return mapToResponse(order);
    }

    public Map<String, Object> getOrderHistoryByCategory(String userId) {
        // Simulate fetching and grouping
        Map<String, Object> history = new HashMap<>();
        history.put("SKINCARE", 2);
        history.put("MAKEUP", 5);
        return history;
    }

    public Map<String, Object> reorder(String userId, String orderId) {
        // Simulate fetching past order and returning as CartResponse
        Map<String, Object> cartResponse = new HashMap<>();
        cartResponse.put("items", new Object[0]);
        cartResponse.put("total", 0.0);
        return cartResponse;
    }

    private BeautyOrderResponse mapToResponse(BeautyOrder order) {
        BeautyOrderResponse response = new BeautyOrderResponse();
        response.setId(order.getId());
        response.setOriginalOrderId(order.getOriginalOrderId());
        response.setGiftWrapping(order.getGiftWrapping());
        response.setGiftMessage(order.getGiftMessage());
        response.setSample(order.isSample());
        response.setBeautyProfileId(order.getBeautyProfileId());
        response.setShadeSelections(order.getShadeSelections());
        response.setSubscriptionType(order.getSubscriptionType());
        return response;
    }
}
