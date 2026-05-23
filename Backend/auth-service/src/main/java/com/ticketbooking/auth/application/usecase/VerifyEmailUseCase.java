package com.ticketbooking.auth.application.usecase;

import com.ticketbooking.auth.domain.event.UserRegistered;
import com.ticketbooking.auth.domain.model.User;
import com.ticketbooking.auth.domain.repository.UserRepository;
import com.ticketbooking.auth.infrastructure.messaging.EventPublisher;
import org.springframework.stereotype.Service;

@Service
public class VerifyEmailUseCase {

    private final UserRepository userRepository;
    private final EventPublisher eventPublisher;

    public VerifyEmailUseCase(UserRepository userRepository, EventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(String token) {
        User user = userRepository.findByVerificationToken(token)
            .orElseThrow(() -> new IllegalArgumentException("Invalid or expired verification token"));

        user.verify();
        userRepository.save(user);

        // Now that user is confirmed, publish the registered event for notification-service
        eventPublisher.publish("user.registered", UserRegistered.of(
            user.getId(), user.getEmail(), user.getFullName()
        ));
    }
}
