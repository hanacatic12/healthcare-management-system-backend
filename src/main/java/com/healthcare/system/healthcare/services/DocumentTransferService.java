package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.Document;
import com.healthcare.system.healthcare.models.dtos.DocumentTransferRequest;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.repositories.DocumentRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

public interface DocumentTransferService {
    List<Document> getAllDocuments();
    List<Document> getDocumentsForUser(Integer userId);
    Document sendDocument(DocumentTransferRequest request);
    List<Document> getReceivedDocuments(Integer userId);
    List<Document> getSentDocuments(Integer userId);
}

@Service
class DocumentTransferServiceImpl implements DocumentTransferService {

    private final DocumentRepository documentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public DocumentTransferServiceImpl(DocumentRepository documentRepository,
                                       DoctorRepository doctorRepository,
                                       PatientRepository patientRepository) {
        this.documentRepository = documentRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }

    private Document mapToDto(com.healthcare.system.healthcare.models.entities.Document entity) {
        return new Document(
                entity.getId(),
                entity.getSender().getDid(),
                entity.getReceiver().getPid(),
                entity.getTitle(),
                entity.getContent(),
                entity.getIsForPatient(),
                "Doctor",
                "Patient"
        );
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> getDocumentsForUser(Integer userId) {
        Doctor doctor = doctorRepository.findById(userId).orElse(null);
        if (doctor != null) {
            return documentRepository.findBySender(doctor).stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        Patient patient = patientRepository.findById(userId).orElse(null);
        if (patient != null) {
            return documentRepository.findByReceiver(patient).stream()
                    .map(this::mapToDto)
                    .collect(Collectors.toList());
        }

        return List.of();
    }

    @Override
    public List<Document> getReceivedDocuments(Integer userId) {
        Patient patient = patientRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return documentRepository.findByReceiver(patient).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> getSentDocuments(Integer userId) {
        Doctor doctor = doctorRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return documentRepository.findBySender(doctor).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Document sendDocument(DocumentTransferRequest request) {
        Doctor sender = doctorRepository.findById(request.getSenderId())
                .orElseThrow(() -> new RuntimeException("Sender doctor not found"));

        Patient receiver = patientRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver patient not found"));

        com.healthcare.system.healthcare.models.entities.Document entity = new com.healthcare.system.healthcare.models.entities.Document();
        entity.setSender(sender);
        entity.setReceiver(receiver);
        entity.setTitle(request.getTitle());
        entity.setContent(request.getContent());
        entity.setIsForPatient(request.isForPatient());

        return mapToDto(documentRepository.save(entity));
    }
}
