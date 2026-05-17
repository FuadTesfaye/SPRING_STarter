package com.app.auth.application.ports;

public interface PasswordService {
    String hash(String password);
    boolean matches(String password, String hash);
}
