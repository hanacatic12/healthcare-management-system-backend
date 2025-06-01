package com.healthcare.system.healthcare.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private String phone;
    private String address;
    private String city;
    private LocalDate dob;
    private String gender;
    private String blood_group;
    private String jmbg;
}
