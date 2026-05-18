package com.company.recommendation.presentation.rest;

import com.company.recommendation.application.service.RecommendationOrchestrator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationOrchestrator orchestrator;

    public RecommendationController(RecommendationOrchestrator orchestrator) {
        this.orchestrator = orchestrator;
    }

    @GetMapping("/{context}/{userId}")
    public ResponseEntity<Map<String, Object>> getRecommendations(
            @PathVariable String context,
            @PathVariable String userId) {
        return ResponseEntity.ok(wrap(orchestrator.getRecommendations(userId, context)));
    }

    @GetMapping("/trending")
    public ResponseEntity<Map<String, Object>> getTrending(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(wrap(orchestrator.getTrending(category)));
    }

    private <T> Map<String, Object> wrap(T data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("success", true);
        return map;
    }
}
