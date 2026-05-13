package com.example.productcrud.service;

import com.example.productcrud.dto.*;

public interface AuthService {

    ApiResponse<RegisterResponse> register(RegisterRequest registerRequest);

    ApiResponse<AuthResponse> login(LoginRequest loginRequest);
}