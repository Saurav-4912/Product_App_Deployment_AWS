package com.example.productcrud.dto;

import com.example.productcrud.enums.Role;
import lombok.Data;

@Data
public class RegisterRequest
{
    private String name;

    private String email;

    private String password;

    private Role role;
}
