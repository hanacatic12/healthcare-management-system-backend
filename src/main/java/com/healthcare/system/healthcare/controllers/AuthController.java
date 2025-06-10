package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.LoginRequest;
import com.healthcare.system.healthcare.models.RegisterRequest;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import com.healthcare.system.healthcare.util.JwtUtil;
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
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

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

        Patient patient = new Patient();
        patient.setUser(savedUser);

        patient.setLastVisit(null);

        patientRepository.save(patient);


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(newUser.getUid(), token));
    }
}




