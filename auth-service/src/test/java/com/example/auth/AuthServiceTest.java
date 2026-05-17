package com.example.auth;

import com.example.auth.application.service.AuthService;
import com.example.auth.domain.model.User;
import com.example.auth.domain.port.UserRepositoryPort;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private final UserRepositoryPort repository = mock(UserRepositoryPort.class);
    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final AuthService authService = new AuthService(repository, restTemplate);

    @Test
    void register_savesUserAndReturnsIt() {
        User user = new User("testuser", "password123");
        when(repository.save(user)).thenReturn(user);

        User result = authService.register(user);

        verify(repository).save(user);
        assertEquals("testuser", result.getUsername());
    }

    @Test
    void register_succeedsEvenIfOrderServiceIsDown() {
        User user = new User("testuser", "password123");
        when(repository.save(user)).thenReturn(user);
        doThrow(new RuntimeException("Connection refused"))
            .when(restTemplate).postForObject(anyString(), any(), eq(String.class));

        User result = authService.register(user);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
    }
}
