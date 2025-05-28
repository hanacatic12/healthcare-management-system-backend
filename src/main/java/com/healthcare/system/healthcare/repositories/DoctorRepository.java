package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
}
