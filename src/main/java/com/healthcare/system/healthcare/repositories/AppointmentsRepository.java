package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentsRepository extends JpaRepository<Appointment, Integer> {
}
