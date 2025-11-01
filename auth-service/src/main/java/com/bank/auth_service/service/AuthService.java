package com.bank.auth_service.service;

import com.bank.auth_service.dtos.AuthResponse;
import com.bank.auth_service.dtos.LoginRequest;
import com.bank.auth_service.dtos.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest signupRequest);
    AuthResponse login(LoginRequest loginRequest);
}
