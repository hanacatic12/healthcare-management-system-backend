package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.PatientResponse;
import com.healthcare.system.healthcare.models.entities.Diagnosis;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.repositories.DiagnosisRepository;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PatientListService {

    private final PatientRepository patientRepository;
    private final DiagnosisRepository diagnosisRepository;

    public PatientListService(PatientRepository patientRepository,
                              DiagnosisRepository diagnosisRepository) {
        this.patientRepository = patientRepository;
        this.diagnosisRepository = diagnosisRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> getPatientsForDoctor(Integer doctorId) {
        return patientRepository.findAll().stream()
                .filter(patient -> Objects.equals(patient.getUser().getUid(), doctorId))
                .collect(Collectors.toList());
    }

    public PatientResponse getPatientDetails(Integer patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        var user = patient.getUser();

        return new PatientResponse(
                patient.getPid(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCity(),
                user.getDob().toString(),
                user.getGender(),
                user.getBlood_group(),
                patient.getDiagnoses()
                        .stream()
                        .map(Diagnosis::getName)
                        .collect(Collectors.toList())
        );
    }

    public void addDiagnosisToPatient(Integer patientId, Integer diagnosisId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        patient.getDiagnoses().add(diagnosis);
        patientRepository.save(patient);
    }

    public void removeDiagnosisFromPatient(Integer patientId, Integer diagnosisId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Diagnosis diagnosis = diagnosisRepository.findById(diagnosisId)
                .orElseThrow(() -> new RuntimeException("Diagnosis not found"));

        patient.getDiagnoses().remove(diagnosis);
        patientRepository.save(patient);
    }
}

