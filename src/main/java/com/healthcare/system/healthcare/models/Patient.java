package com.healthcare.system.healthcare.models;

import java.util.List;

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


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }

    public List<String> getDiagnoses() { return diagnoses; }
    public void setDiagnoses(List<String> diagnoses) { this.diagnoses = diagnoses; }
}
