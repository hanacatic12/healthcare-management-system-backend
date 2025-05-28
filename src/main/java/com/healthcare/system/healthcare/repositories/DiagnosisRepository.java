package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
}
