package com.healthcare.system.healthcare.controllers;


import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.dtos.LoginRequest;
import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
import com.healthcare.system.healthcare.services.AuthService;
//import com.healthcare.system.healthcare.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    //private final AuthenticationManager authenticationManager;
    //private final JwtUtil jwtTokenUtil;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
        /*
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new AuthResponse(""));
        }

        final String jwt = jwtTokenUtil.generateToken(loginRequest.getEmail());

        return ResponseEntity.ok(new AuthResponse(jwt));
     */}


}
