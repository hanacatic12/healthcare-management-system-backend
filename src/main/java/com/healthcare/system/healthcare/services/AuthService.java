package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.dtos.LoginRequest;
import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.UserRepository;
import com.healthcare.system.healthcare.util.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        System.out.println(loginRequest.getEmail());
        System.out.println(loginRequest.getPassword());
        try {
            User user = userRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new RuntimeException("Invalid email or password"));

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {

                throw new RuntimeException("Invalid email or password");
            }

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            return new AuthResponse(user.getUid(), token);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();

        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = new User();
        user.setName(registerRequest.getFirstName() + " " + registerRequest.getLastName());
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setPhone(registerRequest.getPhone());
        user.setAddress(registerRequest.getAddress());
        user.setCity(registerRequest.getCity());
        user.setDob(registerRequest.getDob());
        user.setGender(registerRequest.getGender());
        user.setBlood_group(registerRequest.getBlood_group());
        user.setJmbg(registerRequest.getJmbg());
        user.setRole("patient");

        User savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser.getEmail(), savedUser.getRole());

        return new AuthResponse(savedUser.getUid(), token);
    }
}
