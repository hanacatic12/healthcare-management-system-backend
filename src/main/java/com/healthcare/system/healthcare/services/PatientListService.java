package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.dtos.PatientResponse;
import com.healthcare.system.healthcare.models.Patient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface PatientListService {
    List<Patient> getAllPatients();
    List<Patient> getPatientsForDoctor(Integer doctorId);
    PatientResponse getPatientDetails(Integer patientId);
}

@Service
class PatientListServiceImpl implements PatientListService {

    private final List<Patient> dummyPatients = new ArrayList<>();

    public PatientListServiceImpl() {
        dummyPatients.add(new Patient(1, "John Doe", 10, "john@example.com", "+387 33 975 002", "Hrasnička cesta 3a", "Sarajevo", "01.01.1985", "Male", "A+", List.of("Diabetes")));
        dummyPatients.add(new Patient(2, "Jane Smith", 11, "jane@example.com", "+387 33 975 003", "Obala 3", "Zenica", "15.03.1990", "Female", "B-", List.of("Hypertension")));
        dummyPatients.add(new Patient(3, "Alice Johnson", 10, "alice@example.com", "+387 33 975 004", "Mostar 2", "Mostar", "21.07.1982", "Female", "O+", List.of("Asthma")));
    }

    @Override
    public List<Patient> getAllPatients() {
        return dummyPatients;
    }

    @Override
    public List<Patient> getPatientsForDoctor(Integer doctorId) {
        if (doctorId == null) return Collections.emptyList();
        return dummyPatients.stream()
                .filter(patient -> Objects.equals(patient.getDoctorId(), doctorId))
                .collect(Collectors.toList());
    }

    @Override
    public PatientResponse getPatientDetails(Integer patientId) {
        Patient patient = dummyPatients.stream()
                .filter(p -> p.getId().equals(patientId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getAddress(),
                patient.getCity(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getBloodGroup(),
                patient.getDiagnoses()
        );
    }
}