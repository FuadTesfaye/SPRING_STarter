package com.example.authservice.application.usecases;

import com.example.authservice.application.dto.request.RegisterUserRequest;
import com.example.authservice.application.dto.response.RegisterUserResponse;

public interface RegisterUserService {

    RegisterUserResponse execute(RegisterUserRequest request);
}
