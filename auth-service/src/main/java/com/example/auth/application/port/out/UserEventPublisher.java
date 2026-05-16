package com.example.auth.application.port.out;

import com.example.auth.domain.model.User;

public interface UserEventPublisher {

    void publishUserRegistered(User user);
}
