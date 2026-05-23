package com.ecom.auth.application.port;

import com.ecom.auth.domain.model.User;

public interface TokenProviderPort {
    String generateToken(User user);
}
