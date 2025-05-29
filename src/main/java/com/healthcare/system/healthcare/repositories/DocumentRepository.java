package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Document;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findBySender(Doctor sender);
    List<Document> findByReceiver(Patient receiver);
} 