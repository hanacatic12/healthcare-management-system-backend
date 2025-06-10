package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.DocumentDto;
import com.healthcare.system.healthcare.models.entities.Document;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.repositories.DocumentRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentTransferService {

    private final DocumentRepository documentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;


    @Autowired
    public DocumentTransferService(DocumentRepository documentRepository,
                                   DoctorRepository doctorRepository,
                                   PatientRepository patientRepository) {
        this.documentRepository = documentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    public List<DocumentDto> getAllDocuments() {
        List<Document> documents = documentRepository.findAll();
        return documents.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<DocumentDto> getDocumentsForUser(Integer userId, String role) {
        List<Document> documents;
        if ("DOCTOR".equalsIgnoreCase(role)) {
            documents = documentRepository.findBySender_Did(userId);
        } else if ("PATIENT".equalsIgnoreCase(role)) {
            documents = documentRepository.findByReceiver_Pid(userId);
        } else {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        return documents.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }


    public DocumentDto getDocumentById(Integer id){
        Optional<Document> documentOpt = documentRepository.findById(id);
        if(documentOpt.isEmpty()) {
            throw new IllegalArgumentException("Document not found with ID: " + id);
        }
        return mapToDto(documentOpt.get());
    }

    public void deleteDocument(Integer id){
        if (!documentRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Document not found with ID: " + id);
        }
        documentRepository.deleteById(id);
    }

    private DocumentDto mapToDto(Document document) {
        DocumentDto dto = new DocumentDto();
        dto.setId(document.getId());
        dto.setTitle(document.getTitle());
        dto.setContent(document.getContent());
        dto.setIsForPatient(document.getIsForPatient());
        dto.setSenderId(document.getSender() != null ? document.getSender().getDid() : null);
        dto.setReceiverId(document.getReceiver() != null ? document.getReceiver().getPid() : null);
        dto.setFileName(document.getFileName());
        return dto;
    }

    public DocumentDto sendDocument(DocumentDto request) {
        Document document = new Document();

        document.setTitle(request.getTitle());
        document.setContent(request.getContent());
        document.setFileName(request.getFileName());

        if (request.getSenderId() != null) {
            Doctor sender = doctorRepository.findById(request.getSenderId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid senderId"));
            document.setSender(sender);
        }

        if (request.getReceiverId() != null) {
            Patient receiver = patientRepository.findById(request.getReceiverId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid receiverId"));
            document.setReceiver(receiver);
        }

        document.setIsForPatient(true);

        Document saved = documentRepository.save(document);

        return mapToDto(saved);
    }


}
