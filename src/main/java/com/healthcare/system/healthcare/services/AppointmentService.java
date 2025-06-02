package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.entities.Appointment;
import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.DoctorAppointmentsView;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentsRepository appointmentsRepository;

    public AppointmentService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    public DoctorAppointmentsView getDoctorAppointments(Integer doctorId) {
        List<Appointment> allAppointments = appointmentsRepository.findByDoctor(doctorId);

        List<AppointmentDto> requested = allAppointments.stream()
                .filter(a -> "upcoming".equalsIgnoreCase(a.getStatus()))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        List<AppointmentDto> accepted = allAppointments.stream()
                .filter(a -> "accepted".equalsIgnoreCase(a.getStatus()))
                .map(this::mapToDto)
                .collect(Collectors.toList());

        return new DoctorAppointmentsView(requested, accepted);
    }

    public AppointmentDto updateAppointment(AppointmentDto appointmentDto, String action) {
        Appointment appointment = appointmentsRepository.findById(appointmentDto.getAid())
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if ("accept".equalsIgnoreCase(action)) {
            if (appointmentDto.getDate() == null || appointmentDto.getTime() == null || appointmentDto.getRoom() == 0) {
                throw new IllegalArgumentException("Date, time, and room must be provided when accepting an appointment.");
            }

            appointment.setDate(appointmentDto.getDate());
            appointment.setTime(appointmentDto.getTime());
            appointment.setRoom(appointmentDto.getRoom());
            appointment.setStatus("accepted");

        } else if ("reject".equalsIgnoreCase(action)) {
            appointment.setStatus("rejected");
        } else {
            throw new IllegalArgumentException("Unknown action: " + action);
        }

        Appointment updated = appointmentsRepository.save(appointment);
        return mapToDto(updated);
    }

    public AppointmentDto getAppointmentById(Integer doctorId, Integer appointmentId) {
        Appointment appointment = appointmentsRepository.findById(appointmentId)
                .orElse(null);

        if (appointment != null && appointment.getDoctor().equals(doctorId)) {
            return mapToDto(appointment);
        }

        return null;
    }

    private AppointmentDto mapToDto(Appointment appointment) {
        return new AppointmentDto(
                appointment.getAid(),
                appointment.getPatient(),
                appointment.getDoctor(),
                appointment.getRoom(),
                appointment.getNote(),
                appointment.getDate(),
                appointment.getTime(),
                appointment.getStatus()
        );
    }
}
