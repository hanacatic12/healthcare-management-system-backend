package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
