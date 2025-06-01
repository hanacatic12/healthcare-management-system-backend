package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.DocumentDto;
import com.healthcare.system.healthcare.services.DocumentTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentTransferController {

    private final DocumentTransferService documentTransferService;

    @Autowired
    public DocumentTransferController(DocumentTransferService documentTransferService) {
        this.documentTransferService = documentTransferService;
    }

    @GetMapping("/all")
    public List<DocumentDto> getAllDocuments() {
        return documentTransferService.getAllDocuments();
    }

    @GetMapping("/user/{userId}")
    public List<DocumentDto> getDocumentsForUser(@PathVariable Integer userId) {
        return documentTransferService.getDocumentsForUser(userId);
    }

    @PostMapping("/send")
    public DocumentDto sendDocument(@RequestBody DocumentDto documentDto) {
        return documentTransferService.sendDocument(documentDto);
    }
}
