package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.DocumentDto;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.Document;
import com.healthcare.system.healthcare.models.entities.Patient;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.DocumentRepository;
import com.healthcare.system.healthcare.repositories.PatientRepository;
import com.healthcare.system.healthcare.services.DocumentTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocumentTransferServiceTest {

    private DocumentRepository documentRepository;
    private DoctorRepository doctorRepository;
    private PatientRepository patientRepository;
    private DocumentTransferService service;

    @BeforeEach
    void setUp(){
        documentRepository = mock(DocumentRepository.class);
        doctorRepository = mock(DoctorRepository.class);
        patientRepository = mock(PatientRepository.class);
        service = new DocumentTransferService(documentRepository, doctorRepository, patientRepository);
    }

    @Test
    void testGetAllDocuments(){
        Document doc = new Document();
        doc.setId(1);
        doc.setTitle("X-ray");
        doc.setContent("Details");

        when(documentRepository.findAll()).thenReturn(List.of(doc));

        List<DocumentDto> result = service.getAllDocuments();

        assertEquals(1, result.size());
        assertEquals("X-ray", result.get(0).getTitle());
    }

    @Test
    void testGetDocumentsForDoctor() {
        Document sentDoc = new Document();
        sentDoc.setId(1);
        sentDoc.setTitle("Report");

        when(documentRepository.findBySender_Did(1)).thenReturn(List.of(sentDoc));

        List<DocumentDto> result = service.getDocumentsForUser(1, "DOCTOR");
        assertEquals(1, result.size());
        assertEquals("Report", result.get(0).getTitle());
    }

    @Test
    void testGetDocumentsForPatient() {
        Document receivedDoc = new Document();
        receivedDoc.setId(2);
        receivedDoc.setTitle("Prescription");

        when(documentRepository.findByReceiver_Pid(1)).thenReturn(List.of(receivedDoc));

        List<DocumentDto> result = service.getDocumentsForUser(1, "PATIENT");
        assertEquals(1, result.size());
        assertEquals("Prescription", result.get(0).getTitle());
    }


    @Test
    void testSendDocumentSuccess() {
        DocumentDto dto = new DocumentDto();
        dto.setSenderId(1);
        dto.setReceiverId(2);
        dto.setTitle("Lab Report");
        dto.setContent("Results");
        dto.setIsForPatient(true);

        Doctor doctor = new Doctor();
        doctor.setDid(1);

        Patient patient = new Patient();
        patient.setPid(2);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(2)).thenReturn(Optional.of(patient));

        Document savedDoc = new Document();
        savedDoc.setId(10);
        savedDoc.setTitle(dto.getTitle());
        savedDoc.setContent(dto.getContent());
        savedDoc.setIsForPatient(true);
        savedDoc.setSender(doctor);
        savedDoc.setReceiver(patient);

        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        when(documentRepository.save(captor.capture())).thenReturn(savedDoc);

        DocumentDto result = service.sendDocument(dto);

        assertEquals("Lab Report", result.getTitle());
        assertEquals(1, result.getSenderId());
        assertEquals(2, result.getReceiverId());
        assertTrue(result.getIsForPatient());
    }

    @Test
    void testSendDocumentFailsInvalidDoctor() {
        DocumentDto dto = new DocumentDto();
        dto.setSenderId(99);
        dto.setReceiverId(2);

        when(doctorRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.sendDocument(dto);
        });

        assertTrue(exception.getMessage().contains("Invalid sender doctor ID"));
    }

    @Test
    void testSendDocumentFailsInvalidPatient() {
        DocumentDto dto = new DocumentDto();
        dto.setSenderId(1);
        dto.setReceiverId(99);

        Doctor doctor = new Doctor();
        doctor.setDid(1);

        when(doctorRepository.findById(1)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(99)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.sendDocument(dto);
        });

        assertTrue(exception.getMessage().contains("Invalid receiver patient ID"));
    }

    @Test
    void testGetDocumentByIdSuccess() {
        Document doc = new Document();
        doc.setId(5);
        doc.setTitle("MRI Result");

        when(documentRepository.findById(5)).thenReturn(Optional.of(doc));

        DocumentDto result = service.getDocumentById(5);

        assertEquals(5, result.getId());
        assertEquals("MRI Result", result.getTitle());
    }

    @Test
    void testGetDocumentByIdFails() {
        when(documentRepository.findById(123)).thenReturn(Optional.empty());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.getDocumentById(123);
        });

        assertTrue(ex.getMessage().contains("Document not found with ID"));
    }

    @Test
    void testDeleteDocumentSuccess() {
        when(documentRepository.existsById(8)).thenReturn(true);

        service.deleteDocument(8);

        verify(documentRepository).deleteById(8);
    }

    @Test
    void testDeleteDocumentFails() {
        when(documentRepository.existsById(99)).thenReturn(false);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.deleteDocument(99);
        });

        assertTrue(ex.getMessage().contains("Document not found with ID"));
    }

}
