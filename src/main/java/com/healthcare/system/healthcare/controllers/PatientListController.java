package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.PatientResponse;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.services.PatientListService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class PatientListController {

    private final PatientListService service;

    public PatientListController(PatientListService service) {
        this.service = service;
    }

    @GetMapping("/{doctorId}/patients")
    public List<Patient> getPatientsByDoctor(@PathVariable Integer doctorId) {
        return service.getPatientsForDoctor(doctorId);
    }

    @GetMapping("/patients/{patientId}")
    public PatientResponse getPatientDetails(@PathVariable Integer patientId) {
        return service.getPatientDetails(patientId);
    }

    @PostMapping("/patients/{patientId}/diagnoses/{diagnosisId}")
    public void addDiagnosisToPatient(@PathVariable Integer patientId,
                                      @PathVariable Integer diagnosisId) {
        service.addDiagnosisToPatient(patientId, diagnosisId);
    }

    @DeleteMapping("/patients/{patientId}/diagnoses/{diagnosisId}")
    public void removeDiagnosisFromPatient(@PathVariable Integer patientId,
                                           @PathVariable Integer diagnosisId) {
        service.removeDiagnosisFromPatient(patientId, diagnosisId);
    }
}
