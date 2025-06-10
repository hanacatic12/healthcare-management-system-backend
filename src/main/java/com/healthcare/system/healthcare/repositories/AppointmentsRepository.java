package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientAndStatusIn(Integer patient, List<String> statuses);

    List<Appointment> findByDoctor(Integer doctorId);
}
