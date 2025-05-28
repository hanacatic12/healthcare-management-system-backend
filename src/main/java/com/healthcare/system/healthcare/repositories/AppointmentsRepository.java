package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Appointment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientAndStatusIn(Integer patient, List<String> statuses);
}
