package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

    @Query("SELECT d FROM Doctor d WHERE d.department.did = :did")
    List<Doctor> findByDepartment(Integer did);
}
