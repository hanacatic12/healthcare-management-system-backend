package com.healthcare.system.healthcare.controllers;


import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.dtos.LoginRequest;
import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
import com.healthcare.system.healthcare.services.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
