package com.app.auth.application.ports;

import com.app.auth.domain.User;

public interface TokenService {
    String generateToken(User user);
}
