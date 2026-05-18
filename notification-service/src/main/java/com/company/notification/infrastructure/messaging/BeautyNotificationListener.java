package com.company.notification.infrastructure.messaging;

import com.company.notification.application.service.EmailTemplateService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BeautyNotificationListener {

    private final EmailTemplateService emailTemplateService;

    public BeautyNotificationListener(EmailTemplateService emailTemplateService) {
        this.emailTemplateService = emailTemplateService;
    }

    @RabbitListener(queues = "product.restocked.queue")
    public void handleProductRestocked(Map<String, Object> event) {
        String emailHtml = emailTemplateService.generateBackInStockEmail((String) event.get("productId"), "General");
        System.out.println("Sending email:\n" + emailHtml);
    }

    @RabbitListener(queues = "shade.restock.q")
    public void handleShadeBackInStock(Map<String, Object> event) {
        String emailHtml = emailTemplateService.generateBackInStockEmail((String) event.get("productId"), (String) event.get("shadeCode"));
        System.out.println("Sending shade restock push/email:\n" + emailHtml);
    }

    @RabbitListener(queues = "loyalty.earned.q")
    public void handleLoyaltyPointsEarned(Map<String, Object> event) {
        System.out.println("Push Notification: You earned " + event.get("points") + " Glam Points!");
    }

    @RabbitListener(queues = "tier.upgraded.q")
    public void handleTierUpgraded(Map<String, Object> event) {
        String emailHtml = emailTemplateService.generateTierUpgradedEmail((String) event.get("tier"));
        System.out.println("Sending tier upgrade email:\n" + emailHtml);
    }

    @RabbitListener(queues = "beauty.box.shipped.queue")
    public void handleBeautyBoxShipped(Map<String, Object> event) {
        System.out.println("Sending email: Your beauty box is on the way!");
    }

    @RabbitListener(queues = "new.arrival.queue")
    public void handleNewArrivalForProfile(Map<String, Object> event) {
        System.out.println("Sending email: New arrivals just for you!");
    }
}
