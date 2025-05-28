package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.DocumentTransferRequest;
import com.healthcare.system.healthcare.models.Document;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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

    private final List<Document> dummyDocuments = new ArrayList<>();
    private final AtomicInteger documentIdCounter = new AtomicInteger(5);

    public DocumentTransferServiceImpl() {

        dummyDocuments.add(new Document(1, 10, 20, "Lab Results", "Blood work normal", true, "doctor", "patient"));
        dummyDocuments.add(new Document(2, 20, 10, "Prior Diagnosis", "I had a broken arm", false, "patient", "doctor"));
        dummyDocuments.add(new Document(3, 10, 20, "X-ray Analysis", "Fracture detected", true, "doctor", "patient"));
        dummyDocuments.add(new Document(4, 20, 10, "Prescription History", "Amoxicillin, 2x daily", false, "patient", "doctor"));
    }

    @Override
    public List<Document> getAllDocuments() {
        return dummyDocuments;
    }

    @Override
    public List<Document> getDocumentsForUser(Integer userId) {
        if (userId == null) return new ArrayList<>();
        return dummyDocuments.stream()
                .filter(doc -> Objects.equals(doc.getReceiverId(), userId) || Objects.equals(doc.getSenderId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> getReceivedDocuments(Integer userId) {
        return dummyDocuments.stream()
                .filter(doc -> Objects.equals(doc.getReceiverId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> getSentDocuments(Integer userId) {
        return dummyDocuments.stream()
                .filter(doc -> Objects.equals(doc.getSenderId(), userId))
                .collect(Collectors.toList());
    }

    @Override
    public Document sendDocument(DocumentTransferRequest request) {
        Document newDoc = new Document(
                documentIdCounter.getAndIncrement(),
                request.getSenderId(),
                request.getReceiverId(),
                request.getTitle(),
                request.getContent(),
                request.isForPatient(),
                request.getSenderRole(),
                request.getReceiverRole()
        );
        dummyDocuments.add(newDoc);
        return newDoc;
    }
}
