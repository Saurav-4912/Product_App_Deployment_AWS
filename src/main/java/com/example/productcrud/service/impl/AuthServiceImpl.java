package com.example.productcrud.service.impl;

import com.example.productcrud.dto.*;
import com.example.productcrud.entity.User;
import com.example.productcrud.enums.Role;
import com.example.productcrud.repository.UserRepository;
import com.example.productcrud.security.JwtService;
import com.example.productcrud.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest registerRequest) {

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(registerRequest.getRole() == null ? Role.OWNER : registerRequest.getRole())
                .build();

        User savedUser = userRepository.save(user);

        RegisterResponse registerResponse = new RegisterResponse(
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole().name()
        );

        return new ApiResponse<>(
                true,
                "User registered successfully",
                LocalDateTime.now(),
                registerResponse
        );
    }

    @Override
    public ApiResponse<AuthResponse> login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User.builder()
                        .username(user.getEmail())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build();

        String token = jwtService.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse(
                token,
                "Bearer",
                user.getEmail(),
                user.getRole().name()
        );

        return new ApiResponse<>(
                true,
                "Login successful",
                LocalDateTime.now(),
                authResponse
        );
    }
}