package com.company.loyalty.application.service;

import com.company.loyalty.domain.model.LoyaltyAccount;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoyaltyService {

    private final RabbitTemplate rabbitTemplate;

    public LoyaltyService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public LoyaltyAccount getAccount(String userId) {
        LoyaltyAccount acc = new LoyaltyAccount();
        acc.setUserId(userId);
        acc.setTier("BRONZE");
        acc.setCurrentPoints(0);
        acc.setLifetimePoints(0);
        acc.setMemberSince(LocalDate.now().toString());
        return acc;
    }

    public void earnPoints(String userId, String source, String referenceId, double amount) {
        int pointsToEarn = 0;
        if ("ORDER".equals(source)) {
            pointsToEarn = (int) Math.floor(amount); // tier multiplier logic goes here
        } else if ("REVIEW".equals(source)) {
            pointsToEarn = 50;
        } else if ("PROFILE_COMPLETE".equals(source)) {
            pointsToEarn = 100;
        }

        // Add transaction and update balance
        Map<String, Object> event = new HashMap<>();
        event.put("userId", userId);
        event.put("points", pointsToEarn);
        rabbitTemplate.convertAndSend("beauty.events", "loyalty.points.earned", event);
        
        checkTierUpgrade(userId, pointsToEarn); // Send new lifetime points
    }

    public void checkTierUpgrade(String userId, int newLifetimePoints) {
        // Upgrade logic based on thresholds
        // BRONZE 0, SILVER 500, GOLD 2000, PLATINUM 5000, DIAMOND 10000
        if (newLifetimePoints >= 500) {
            Map<String, Object> event = new HashMap<>();
            event.put("userId", userId);
            event.put("tier", "SILVER");
            rabbitTemplate.convertAndSend("beauty.events", "tier.upgraded", event);
        }
    }

    public void redeemPoints(String userId, String rewardId) {
        // deduct points logic
    }

    public List<Object> getAvailableRewards(String userId) {
        return new ArrayList<>();
    }

    public List<Object> getTransactionHistory(String userId) {
        return new ArrayList<>();
    }
}
