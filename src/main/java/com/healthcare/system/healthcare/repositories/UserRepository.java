package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
