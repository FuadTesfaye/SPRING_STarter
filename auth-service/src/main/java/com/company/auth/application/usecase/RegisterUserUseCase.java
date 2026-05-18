package com.company.auth.application.usecase;

import com.company.auth.application.dto.UserDTO;
import com.company.auth.domain.event.UserRegisteredEvent;
import com.company.auth.domain.model.User;
import com.company.auth.domain.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationEventPublisher;

@Service
@RequiredArgsConstructor
public class RegisterUserUseCase {
    private final IUserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public UserDTO execute(String username, String email, String password) {
        User user = new User(username, email, password);
        User savedUser = userRepository.save(user);
        
        // Publish internal event for infrastructure messaging to pick up
        eventPublisher.publishEvent(new UserRegisteredEvent(savedUser.getUsername(), savedUser.getEmail()));
        
        UserDTO dto = new UserDTO();
        dto.setId(savedUser.getId());
        dto.setUsername(savedUser.getUsername());
        dto.setEmail(savedUser.getEmail());
        return dto;
    }
}
