package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.entities.Appointment;
import com.healthcare.system.healthcare.models.DoctorAppointmentsView;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AppointmentServiceUnitTest {

    @Mock
    private AppointmentsRepository appointmentsRepository;

    private AppointmentService appointmentService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentService(appointmentsRepository);
    }

    private Appointment createMockAppointment(int aid, int patientId, int doctorId, String status) {
        Appointment appointment = new Appointment();
        appointment.setAid(aid);
        appointment.setPatient(patientId);
        appointment.setDoctor(doctorId);
        appointment.setRoom(1);
        appointment.setNote("Follow-up note");
        appointment.setDate(LocalDate.now());
        appointment.setTime(LocalTime.now());
        appointment.setStatus(status);
        return appointment;
    }

    @Test
    public void testGetDoctorAppointments_splitsByStatus() {
        int doctorId = 101;
        Appointment upcoming = createMockAppointment(1, 201, doctorId, "upcoming");
        Appointment accepted = createMockAppointment(2, 202, doctorId, "accepted");

        when(appointmentsRepository.findByDoctor(doctorId)).thenReturn(List.of(upcoming, accepted));

        DoctorAppointmentsView view = appointmentService.getDoctorAppointments(doctorId);

        assertThat(view.getRequested()).hasSize(1);
        assertThat(view.getRequested().get(0).getStatus()).isEqualToIgnoringCase("upcoming");

        assertThat(view.getUpcoming()).hasSize(1);
        assertThat(view.getUpcoming().get(0).getStatus()).isEqualToIgnoringCase("accepted");
    }

    @Test
    public void testUpdateAppointment_acceptsAppointmentWithValidData() {
        Appointment appointment = createMockAppointment(1, 123, 101, "upcoming");
        AppointmentDto dto = new AppointmentDto(1, 123, 101, 5, "Updated note",
                LocalDate.now().plusDays(1), LocalTime.of(14, 0), "upcoming");

        when(appointmentsRepository.findById(1)).thenReturn(Optional.of(appointment));
        when(appointmentsRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentDto updatedDto = appointmentService.updateAppointment(dto, "accept");

        assertThat(updatedDto.getStatus()).isEqualTo("accepted");
        assertThat(updatedDto.getRoom()).isEqualTo(5);
        assertThat(updatedDto.getDate()).isEqualTo(dto.getDate());
        assertThat(updatedDto.getTime()).isEqualTo(dto.getTime());

        verify(appointmentsRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testUpdateAppointment_rejectsAppointment() {
        Appointment appointment = createMockAppointment(2, 124, 101, "upcoming");
        AppointmentDto dto = new AppointmentDto(2, 124, 101, 0, "Reject note",
                null, null, "upcoming");

        when(appointmentsRepository.findById(2)).thenReturn(Optional.of(appointment));
        when(appointmentsRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AppointmentDto result = appointmentService.updateAppointment(dto, "reject");

        assertThat(result.getStatus()).isEqualTo("rejected");
        verify(appointmentsRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    public void testUpdateAppointment_acceptWithoutRequiredFields_throwsException() {
        Appointment appointment = createMockAppointment(3, 125, 101, "upcoming");
        AppointmentDto dto = new AppointmentDto(3, 125, 101, 0, "Missing fields",
                null, null, "upcoming");

        when(appointmentsRepository.findById(3)).thenReturn(Optional.of(appointment));

        assertThatThrownBy(() -> appointmentService.updateAppointment(dto, "accept"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Date, time, and room must be provided");
    }

    @Test
    public void testUpdateAppointment_withInvalidId_throwsException() {
        AppointmentDto dto = new AppointmentDto(999, 126, 101, 1, "Invalid ID",
                LocalDate.now(), LocalTime.now(), "upcoming");

        when(appointmentsRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> appointmentService.updateAppointment(dto, "accept"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Appointment not found");
    }

    @Test
    public void testGetAppointmentById_returnsDtoWhenDoctorMatches() {
        int doctorId = 555;
        int appointmentId = 1;
        Appointment appointment = createMockAppointment(appointmentId, 200, doctorId, "upcoming");

        when(appointmentsRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        AppointmentDto dto = appointmentService.getAppointmentById(doctorId, appointmentId);

        assertThat(dto).isNotNull();
        assertThat(dto.getAid()).isEqualTo(appointmentId);
        assertThat(dto.getDoctorId()).isEqualTo(doctorId);
    }

    @Test
    public void testGetAppointmentById_returnsNullWhenDoctorDoesNotMatch() {
        int doctorId = 555;
        int wrongDoctorId = 999;
        int appointmentId = 1;
        Appointment appointment = createMockAppointment(appointmentId, 200, doctorId, "upcoming");

        when(appointmentsRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));

        AppointmentDto dto = appointmentService.getAppointmentById(wrongDoctorId, appointmentId);

        assertThat(dto).isNull();
    }

    @Test
    public void testGetAppointmentById_returnsNullWhenAppointmentNotFound() {
        when(appointmentsRepository.findById(1234)).thenReturn(Optional.empty());

        AppointmentDto dto = appointmentService.getAppointmentById(101, 1234);

        assertThat(dto).isNull();
    }

    @Test
    public void testUpdateAppointment_withUnknownAction_throwsException() {
        Appointment appointment = createMockAppointment(4, 127, 101, "upcoming");
        AppointmentDto dto = new AppointmentDto(4, 127, 101, 1, "Some note",
                LocalDate.now(), LocalTime.now(), "upcoming");

        when(appointmentsRepository.findById(4)).thenReturn(Optional.of(appointment));

        assertThatThrownBy(() -> appointmentService.updateAppointment(dto, "cancel"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown action");
    }

}
