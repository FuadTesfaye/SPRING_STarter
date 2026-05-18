package com.company.recommendation.application.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendationOrchestrator {

    public List<Object> getRecommendations(String userId, String context) {
        // Mock orchestration of different strategies based on context
        // Returns a list of RecommendedProduct objects
        return new ArrayList<>();
    }

    public List<Object> getTrending(String category) {
        return new ArrayList<>();
    }
}
