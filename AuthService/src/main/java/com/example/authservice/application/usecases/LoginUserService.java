package com.example.authservice.application.usecases;

import com.example.authservice.application.dto.request.LoginUserRequest;
import com.example.authservice.application.dto.response.LoginUserResponse;

public interface LoginUserService {

    LoginUserResponse execute(LoginUserRequest request);
}
