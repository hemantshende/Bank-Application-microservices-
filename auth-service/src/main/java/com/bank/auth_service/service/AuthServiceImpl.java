package com.bank.auth_service.service;

import com.bank.auth_service.Util_Important.JwtUtil;
import com.bank.auth_service.dtos.AuthResponse;
import com.bank.auth_service.dtos.LoginRequest;
import com.bank.auth_service.dtos.SignupRequest;
import com.bank.auth_service.entity.User;
import com.bank.auth_service.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        if(userRepository.existsByUsername(signupRequest.getUsername())){
            return new AuthResponse("user already registered..!",null);
        }
        User user = User.builder()
                .username(signupRequest.getUsername())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .userType(signupRequest.getUserType())
                .build();

        userRepository.save(user);
        return new AuthResponse("User registered successfully!", null);
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new AuthResponse("Invalid username or password!", null);
        }

        String token= jwtUtil.generateToken(user.getUsername(), user.getUserType().name());

        return new AuthResponse("Login successful!", token);
    }
}
