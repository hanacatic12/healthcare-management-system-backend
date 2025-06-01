package com.healthcare.system.healthcare.repositories;

import com.healthcare.system.healthcare.models.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    List<Document> findBySender_Did(Integer senderId);
    List<Document> findByReceiver_Pid(Integer receiverId);
}
