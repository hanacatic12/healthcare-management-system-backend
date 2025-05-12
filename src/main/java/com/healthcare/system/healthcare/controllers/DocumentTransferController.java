package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.dtos.DocumentTransferRequest;
import com.healthcare.system.healthcare.models.Document;
import com.healthcare.system.healthcare.services.DocumentTransferService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentTransferController {

    private final DocumentTransferService service;

    public DocumentTransferController(DocumentTransferService service) {
        this.service = service;
    }

    @GetMapping
    public List<Document> getAllDocuments() {
        return service.getAllDocuments();
    }
    @GetMapping("/received/{userId}")
    public List<Document> getReceived(@PathVariable Integer userId) {
        return service.getReceivedDocuments(userId);
    }

    @GetMapping("/sent/{userId}")
    public List<Document> getSent(@PathVariable Integer userId) {
        return service.getSentDocuments(userId);
    }


    @GetMapping("/user/{userId}")
    public List<Document> getDocumentsForUser(@PathVariable Integer userId) {
        return service.getDocumentsForUser(userId);
    }

    @PostMapping ("/send")
    public Document sendDocument(@RequestBody DocumentTransferRequest request) {
        return service.sendDocument(request);
    }
}

