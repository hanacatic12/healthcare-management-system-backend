package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Department, Integer> {
}
