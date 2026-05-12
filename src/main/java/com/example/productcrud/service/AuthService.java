package com.example.productcrud.service;

import com.example.productcrud.dto.AuthResponse;
import com.example.productcrud.dto.LoginRequest;
import com.example.productcrud.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}