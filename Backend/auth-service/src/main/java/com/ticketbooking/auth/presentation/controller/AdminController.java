package com.ticketbooking.auth.presentation.controller;

import com.ticketbooking.auth.domain.model.User;
import com.ticketbooking.auth.domain.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private static final String ADMIN_KEY = "philemondan32@gmail.com:Dantab@4040";

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void requireAdmin(String adminKey) {
        if (!ADMIN_KEY.equals(adminKey)) {
            throw new SecurityException("Unauthorized");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserSummary>> listUsers(
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        List<UserSummary> users = userRepository.findAll().stream()
            .map(UserSummary::from)
            .toList();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> deleteUser(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "User deleted"));
    }

    @PostMapping("/users/{id}/ban")
    public ResponseEntity<UserSummary> banUser(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.ban();
        return ResponseEntity.ok(UserSummary.from(userRepository.save(user)));
    }

    @PostMapping("/users/{id}/unban")
    public ResponseEntity<UserSummary> unbanUser(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.unban();
        return ResponseEntity.ok(UserSummary.from(userRepository.save(user)));
    }

    @PostMapping("/users/{id}/promote")
    public ResponseEntity<UserSummary> promoteUser(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.promoteToAdmin();
        return ResponseEntity.ok(UserSummary.from(userRepository.save(user)));
    }

    @PostMapping("/users/{id}/demote")
    public ResponseEntity<UserSummary> demoteUser(
            @PathVariable UUID id,
            @RequestHeader(value = "X-Admin-Key", required = false) String key) {
        requireAdmin(key);
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.demoteToUser();
        return ResponseEntity.ok(UserSummary.from(userRepository.save(user)));
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<Map<String, String>> handleUnauth(SecurityException ex) {
        return ResponseEntity.status(403).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    public record UserSummary(
        String id,
        String email,
        String fullName,
        String role,
        boolean verified,
        boolean banned,
        Instant createdAt
    ) {
        static UserSummary from(User u) {
            return new UserSummary(
                u.getId().toString(), u.getEmail(), u.getFullName(),
                u.getRole(), u.isVerified(), u.isBanned(), u.getCreatedAt()
            );
        }
    }
}
