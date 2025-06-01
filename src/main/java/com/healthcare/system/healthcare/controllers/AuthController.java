package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.LoginRequest;
import com.healthcare.system.healthcare.models.RegisterRequest;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import com.healthcare.system.healthcare.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PatientRepository patientRepository;
    private PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        // Autentifikacija
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // Pronađi user-a u bazi
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generiši token
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Vrati samo token + userId
        return ResponseEntity.ok(new AuthResponse(user.getUid(), token));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("First Name: " + request.getFirstName());
        System.out.println("Last Name: " + request.getLastName());
        System.out.println("Gender: " + request.getGender());
        System.out.println("DOB: " + request.getDob());
        System.out.println("City: " + request.getCity());
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Email already exists!"));
        }

        if(!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.badRequest().body(new AuthResponse(null, "Passwords do not match!"));
        }


        User newUser = new User();
        newUser.setName(request.getFirstName() + " " + request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setPhone(request.getPhone());
        newUser.setAddress(request.getAddress());
        newUser.setCity(request.getCity());
        newUser.setDob(request.getDob());
        newUser.setGender(request.getGender());
        newUser.setBlood_group(request.getBlood_group());
        newUser.setRole("patient");
        newUser.setJmbg(request.getJmbg());
        User savedUser = userRepository.save(newUser);

        // Kreiraj Patient, setuj managed User i ostale podatke
        Patient patient = new Patient();
        patient.setUser(savedUser);  // Važno: savedUser je managed entity!

        patient.setLastVisit(null);

        // Sačuvaj Patient
        patientRepository.save(patient);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Vrati token + userId
        return ResponseEntity.ok(new AuthResponse(newUser.getUid(), token));
    }
}



/*
import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.dtos.LoginRequest;
import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
import com.healthcare.system.healthcare.services.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    private static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}

 */


//package com.healthcare.system.healthcare.controllers;
//
//import com.healthcare.system.healthcare.models.dtos.AuthResponse;
//import com.healthcare.system.healthcare.models.dtos.LoginRequest;
//import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
//import com.healthcare.system.healthcare.services.AuthService;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//@RequiredArgsConstructor
//public class AuthController {
//    private final AuthService authService;
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
//        try {
//            AuthResponse response = authService.register(registerRequest);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            AuthResponse response = authService.login(loginRequest);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(new ErrorResponse(e.getMessage()));
//        }
//    }
//
//    private static class ErrorResponse {
//        private final String message;
//
//        public ErrorResponse(String message) {
//            this.message = message;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//    }
//}
