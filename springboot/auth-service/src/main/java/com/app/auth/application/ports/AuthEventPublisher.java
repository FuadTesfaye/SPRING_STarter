package com.app.auth.application.ports;

import com.app.auth.domain.User;

public interface AuthEventPublisher {
    void publishUserRegistered(User user);
}
