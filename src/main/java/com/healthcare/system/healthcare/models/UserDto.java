package com.healthcare.system.healthcare.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private Integer uid;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
    private LocalDate dob;
    private String gender;
    private String blood_group;
    private LocalDate last_visit;
    private String department;
    private String work_start;
    private String work_end;
    private String role;
    private String jmbg;
}
