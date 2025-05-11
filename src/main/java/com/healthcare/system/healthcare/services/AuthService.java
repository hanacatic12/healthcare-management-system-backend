package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AuthResponse;
import com.healthcare.system.healthcare.models.dtos.LoginRequest;
import com.healthcare.system.healthcare.models.dtos.RegisterRequest;
import com.healthcare.system.healthcare.models.dtos.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    private List<UserDto> users = new ArrayList<>();
    private Integer counter = 0;

    public AuthService() {
        users.add(new UserDto(
                counter++,
                "John Doe",
                "john.doe@gmail.com",
                "password123",
                "+387 33 975 002",
                "Hrasnička cesta 3a",
                "Sarajevo",
                LocalDate.parse("1985-01-01"),
                "Male",
                "A+",
                LocalDate.parse("2024-02-15"),
                null,
                null,
                null,
                "patient",
                "1105993710028"
        ));
    }

    public AuthResponse login(LoginRequest loginRequest) {
        UserDto user = users.stream().filter(u -> u.getEmail().equals(loginRequest.getEmail()) && u.getPassword().equals(loginRequest.getPassword())).findFirst().orElse(null);

        if (user == null) {throw new RuntimeException("Invalid credentials");}
        else {
            String token = "dummy-token-" + user.getEmail();
            return new AuthResponse(token, user.getEmail(), user.getUid(), user.getName());
        }
    }

    public AuthResponse register(RegisterRequest registerRequest) {
        System.out.println(registerRequest.getEmail());
        UserDto newUser = new UserDto(
                counter++,
                registerRequest.getFirstName() + " " + registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getPhone(),
                registerRequest.getAddress(),
                registerRequest.getCity(),
                registerRequest.getDob(),
                registerRequest.getGender(),
                registerRequest.getBlood_group(),
                null,
                null,
                null,
                null,
                "patient",
                registerRequest.getJmbg()
        );

        String token = "dummy-token-" + newUser.getEmail();
        return new AuthResponse(token, newUser.getEmail(), newUser.getUid(), newUser.getName());
    }
}
