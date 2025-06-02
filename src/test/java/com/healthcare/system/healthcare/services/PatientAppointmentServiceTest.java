package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.dtos.DepartmentDto;
import com.healthcare.system.healthcare.models.entities.Appointment;
import com.healthcare.system.healthcare.models.entities.Department;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import com.healthcare.system.healthcare.repositories.DepartmentsRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatientAppointmentServiceTest {

    @Mock
    private AppointmentsRepository appointmentsRepository;

    @Mock
    private DepartmentsRepository departmentsRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PatientAppointmentService patientAppointmentService;

    @Test
    public void givenAppointments_whenGetAppointments_thenListShouldBeFound() {
        Integer patientId = 1;
        Appointment appointment = new Appointment();
        appointment.setAid(1);
        appointment.setPatient(patientId);
        appointment.setDoctor(10);
        appointment.setStatus("upcoming");

        when(appointmentsRepository.findByPatientAndStatusIn(patientId, List.of("upcoming", "accepted")))
                .thenReturn(List.of(appointment));

        List<AppointmentDto> result = patientAppointmentService.getAppointments(patientId);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getAid().intValue());
    }

    @Test
    public void givenNoAppointments_whenGetAppointments_thenListShouldBeEmpty() {
        Integer patientId = 1;
        when(appointmentsRepository.findByPatientAndStatusIn(patientId, List.of("upcoming", "accepted")))
                .thenReturn(Collections.emptyList());

        List<AppointmentDto> result = patientAppointmentService.getAppointments(patientId);

        assertTrue(result.isEmpty());
    }

    @Test
    public void givenExistingAppointment_whenGetSingleAppointment_thenDtoShouldBeReturned() {
        Integer aid = 1;
        Appointment appointment = new Appointment();
        appointment.setAid(aid);
        appointment.setPatient(123);
        appointment.setDoctor(10);
        appointment.setStatus("upcoming");

        when(appointmentsRepository.findById(aid)).thenReturn(Optional.of(appointment));

        AppointmentDto result = patientAppointmentService.getSingleAppointment(aid);

        assertNotNull(result);
        assertEquals(aid, result.getAid());
        assertEquals(123, result.getPatientId());
    }

    @Test
    public void givenNonExistingAppointment_whenGetSingleAppointment_thenThrowException() {
        Integer aid = 2;
        when(appointmentsRepository.findById(aid)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patientAppointmentService.getSingleAppointment(aid);
        });

        assertEquals("No appointment found", exception.getMessage());
    }

    @Test
    public void whenBookAppointment_thenAppointmentIsSavedAndDtoReturned() {
        Integer uid = 1;
        AppointmentDto newAppointmentDto = new AppointmentDto(null, null, 10, null, "Checkup", null, null, null);

        ArgumentCaptor<Appointment> appointmentCaptor = ArgumentCaptor.forClass(Appointment.class);

        AppointmentDto result = patientAppointmentService.bookAppointment(newAppointmentDto, uid);

        verify(appointmentsRepository).save(appointmentCaptor.capture());
        Appointment savedAppointment = appointmentCaptor.getValue();

        assertEquals(uid, savedAppointment.getPatient());
        assertEquals(10, savedAppointment.getDoctor());
        assertEquals("Checkup", savedAppointment.getNote());
        assertEquals("upcoming", savedAppointment.getStatus());

        assertNotNull(result);
        assertEquals(uid, result.getPatientId());
        assertEquals(10, result.getDoctorId());
        assertEquals("Checkup", result.getNote());
        assertEquals("upcoming", result.getStatus());
    }

    @Test
    public void givenExistingAppointment_whenCancelAppointment_thenStatusCancelled() {
        Integer aid = 1;
        Appointment appointment = new Appointment();
        appointment.setAid(aid);
        appointment.setStatus("upcoming");

        when(appointmentsRepository.findById(aid)).thenReturn(Optional.of(appointment));

        AppointmentDto result = patientAppointmentService.cancelAppointment(aid);

        verify(appointmentsRepository).save(appointment);

        assertNotNull(result);
        assertEquals("cancelled", result.getStatus());
    }

    @Test
    public void givenNonExistingAppointment_whenCancelAppointment_thenThrowException() {
        Integer aid = 2;
        when(appointmentsRepository.findById(aid)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            patientAppointmentService.cancelAppointment(aid);
        });

        assertEquals("Appointment not found", exception.getMessage());
    }

    @Test
    public void whenGetDepartments_thenListShouldBeReturned() {
        Department department1 = new Department();
        department1.setDid(1);
        department1.setName("Cardiology");
        department1.setFloor(2);
        department1.setDescription("Department of cardiology");

        Department department2 = new Department();
        department2.setDid(2);
        department2.setName("Oncology");
        department2.setFloor(3);
        department2.setDescription("Department of oncology");

        List<Department> departments = List.of(department1, department2);

        when(departmentsRepository.findAll()).thenReturn(departments);

        List<DepartmentDto> result = patientAppointmentService.getDepartments();

        assertEquals(2, result.size());
        assertEquals("Cardiology", result.get(0).getName());
        assertEquals("Oncology", result.get(1).getName());
    }

    @Test
    public void whenNoDepartments_thenEmptyListReturned() {
        when(departmentsRepository.findAll()).thenReturn(Collections.emptyList());

        List<DepartmentDto> result = patientAppointmentService.getDepartments();

        assertTrue(result.isEmpty());
    }
}
