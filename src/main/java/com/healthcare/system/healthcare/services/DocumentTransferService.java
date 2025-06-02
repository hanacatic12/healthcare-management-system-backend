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

    public List<DocumentDto> getDocumentsForUser(Integer userId) {
        List<Document> sentDocs = documentRepository.findBySender_Did(userId);
        List<Document> receivedDocs = documentRepository.findByReceiver_Pid(userId);

        List<Document> allDocs = new ArrayList<>();
        allDocs.addAll(sentDocs);
        allDocs.addAll(receivedDocs);

        return allDocs.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public DocumentDto sendDocument(DocumentDto dto) {
        Document document = new Document();

        Optional<Doctor> senderOpt = doctorRepository.findById(dto.getSenderId());
        if (senderOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid sender doctor ID: " + dto.getSenderId());
        }
        document.setSender(senderOpt.get());

        Optional<Patient> receiverOpt = patientRepository.findById(dto.getReceiverId());
        if (receiverOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid receiver patient ID: " + dto.getReceiverId());
        }
        document.setReceiver(receiverOpt.get());

        document.setTitle(dto.getTitle());
        document.setContent(dto.getContent());
        document.setIsForPatient(dto.getIsForPatient());

        documentRepository.save(document);

        return mapToDto(document);
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
        return dto;
    }
}
