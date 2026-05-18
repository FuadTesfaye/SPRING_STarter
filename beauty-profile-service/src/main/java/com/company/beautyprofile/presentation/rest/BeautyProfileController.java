package com.company.beautyprofile.presentation.rest;

import com.company.beautyprofile.application.dto.QuizAnswers;
import com.company.beautyprofile.application.service.BeautyProfileService;
import com.company.beautyprofile.domain.model.BeautyProfile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/beauty-profile")
public class BeautyProfileController {

    private final BeautyProfileService service;

    public BeautyProfileController(BeautyProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createOrUpdateProfile(
            @RequestHeader("X-User-Id") String userId,
            @RequestBody BeautyProfile request) {
        return ResponseEntity.ok(wrap(service.createOrUpdateProfile(userId, request)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> getProfile(@PathVariable String userId) {
        return ResponseEntity.ok(wrap(service.getProfile(userId)));
    }

    @PutMapping("/{userId}/shade")
    public ResponseEntity<Map<String, Object>> updateShadeProfile(
            @PathVariable String userId,
            @RequestBody Object request) {
        return ResponseEntity.ok(wrap(service.updateShadeProfile(userId, request)));
    }

    @GetMapping("/{userId}/recommendations")
    public ResponseEntity<Map<String, Object>> getRecommendations(@PathVariable String userId) {
        return ResponseEntity.ok(wrap(service.getRecommendations(userId)));
    }

    @PostMapping("/{userId}/routine")
    public ResponseEntity<Map<String, Object>> saveRoutine(
            @PathVariable String userId,
            @RequestBody Object request) {
        return ResponseEntity.ok(wrap(service.saveRoutine(userId, request)));
    }

    @GetMapping("/{userId}/routines")
    public ResponseEntity<Map<String, Object>> getRoutines(@PathVariable String userId) {
        return ResponseEntity.ok(wrap(service.getRoutines(userId)));
    }

    @PostMapping("/quiz")
    public ResponseEntity<Map<String, Object>> processQuiz(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestBody QuizAnswers answers) {
        return ResponseEntity.ok(wrap(service.processQuiz(answers, userId)));
    }

    private <T> Map<String, Object> wrap(T data) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("success", true);
        return map;
    }
}
