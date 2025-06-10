package com.healthcare.system.healthcare.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Patient {
    private Integer id;
    private String name;
    private Integer doctorId;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String dateOfBirth;
    private String gender;
    private String bloodGroup;
    private List<String> diagnoses;

    public Patient(Integer id, String name, Integer doctorId, String email, String phone, String address, String city,
                   String dateOfBirth, String gender, String bloodGroup, List<String> diagnoses) {
        this.id = id;
        this.name = name;
        this.doctorId = doctorId;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.diagnoses = diagnoses;
    }

}
