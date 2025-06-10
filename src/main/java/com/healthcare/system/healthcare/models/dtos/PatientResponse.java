package com.healthcare.system.healthcare.models.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PatientResponse {
    private Integer id;
    private String name;
    public String email;
    public String phone;
    public String address;
    public String city;
    public String dateOfBirth;
    public String gender;
    public String bloodGroup;
    public List<String> diagnoses;

    public PatientResponse(Integer id, String name, String email, String phone,
                                  String address, String city, String dateOfBirth,
                                  String gender, String bloodGroup, List<String> diagnoses) {
        this.id = id;
        this.name = name;
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


