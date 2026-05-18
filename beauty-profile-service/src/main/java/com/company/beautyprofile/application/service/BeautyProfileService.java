package com.company.beautyprofile.application.service;

import com.company.beautyprofile.application.dto.QuizAnswers;
import com.company.beautyprofile.domain.model.BeautyProfile;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BeautyProfileService {

    private final RabbitTemplate rabbitTemplate;

    public BeautyProfileService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public BeautyProfile createOrUpdateProfile(String userId, BeautyProfile request) {
        request.setUserId(userId);
        request.setCompletionStatus("COMPLETE");
        // Save logic
        return request;
    }

    public BeautyProfile getProfile(String userId) {
        BeautyProfile p = new BeautyProfile();
        p.setUserId(userId);
        return p;
    }

    public Object updateShadeProfile(String userId, Object shadeProfileRequest) {
        return shadeProfileRequest;
    }

    public List<Object> getRecommendations(String userId) {
        // Build filters, call product service (mocked here)
        return new ArrayList<>();
    }

    public Object saveRoutine(String userId, Object routineRequest) {
        return routineRequest;
    }

    public List<Object> getRoutines(String userId) {
        return new ArrayList<>();
    }

    public BeautyProfile processQuiz(QuizAnswers answers, String userId) {
        BeautyProfile profile = new BeautyProfile();
        profile.setUserId(userId != null ? userId : UUID.randomUUID().toString());
        profile.setSkinType(answers.getSkinType());
        profile.setSkinConcerns(answers.getSkinConcerns());
        profile.setSkinTone(answers.getSkinTone());
        profile.setUndertone(answers.getUndertone());
        profile.setHairType(answers.getHairType());
        profile.setHairConcerns(answers.getHairConcerns());
        profile.setAvoidIngredients(answers.getAvoidIngredients());
        profile.setPrefersCleanBeauty(Boolean.TRUE.equals(answers.getPrefersCleanBeauty()));
        profile.setPrefersVegan(Boolean.TRUE.equals(answers.getPrefersVegan()));
        profile.setPrefersCrueltyFree(Boolean.TRUE.equals(answers.getPrefersCrueltyFree()));
        profile.setBudgetRange(answers.getBudgetRange());
        profile.setCompletionStatus("COMPLETE");
        
        // Save
        rabbitTemplate.convertAndSend("beauty.events", "beauty.profile.completed", profile.getUserId());
        return profile;
    }
}
