package com.company.loyalty.presentation.rest;

import com.company.loyalty.application.service.LoyaltyService;
import com.company.loyalty.domain.model.LoyaltyAccount;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/loyalty")
public class LoyaltyController {

    private final LoyaltyService service;

    public LoyaltyController(LoyaltyService service) {
        this.service = service;
    }

    @GetMapping("/account/{userId}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable String userId) {
        return ResponseEntity.ok(wrap(service.getAccount(userId)));
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<Map<String, Object>> getTransactions(@PathVariable String userId) {
        return ResponseEntity.ok(wrap(service.getTransactionHistory(userId)));
    }

    @GetMapping("/rewards")
    public ResponseEntity<Map<String, Object>> getRewards(@RequestHeader("X-User-Id") String userId) {
        return ResponseEntity.ok(wrap(service.getAvailableRewards(userId)));
    }

    @PostMapping("/redeem")
    public ResponseEntity<Map<String, Object>> redeemPoints(@RequestBody Map<String, String> request) {
        service.redeemPoints(request.get("userId"), request.get("rewardId"));
        return ResponseEntity.ok(wrap(null));
    }

    private <T> Map<String, Object> wrap(T data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("success", true);
        return map;
    }
}
