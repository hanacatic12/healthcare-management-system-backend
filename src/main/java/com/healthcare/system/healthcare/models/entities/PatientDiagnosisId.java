package com.healthcare.system.healthcare.models.entities;

import java.io.Serializable;
import java.util.Objects;

public class PatientDiagnosisId implements Serializable {

    private Integer patient;
    private Integer diagnosis;

    public PatientDiagnosisId() {
    }

    public PatientDiagnosisId(Integer patient, Integer diagnosis) {
        this.patient = patient;
        this.diagnosis = diagnosis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PatientDiagnosisId)) return false;
        PatientDiagnosisId that = (PatientDiagnosisId) o;
        return Objects.equals(patient, that.patient) && Objects.equals(diagnosis, that.diagnosis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patient, diagnosis);
    }
}

