package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.entities.Appointment;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientAppointmentService {
//    private List<AppointmentDto> appointments = new ArrayList<>();
//    private Integer counter = 4;

    private AppointmentsRepository appointmentsRepository;

    public PatientAppointmentService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
//        appointments.add(new AppointmentDto(
//                1,
//                123,
//                456,
//                0,
//                "Available on Monday.",
//                LocalDate.parse("2025-06-10"),
//                LocalTime.parse("10:00"),
//                "upcoming"
//        ));
//
//        appointments.add(new AppointmentDto(
//                2,
//                123,
//                456,
//                22,
//                "Afternoons on Thursday.",
//                LocalDate.parse("2025-06-10"),
//                LocalTime.parse("10:00"),
//                "accepted"
//        ));
//
//        appointments.add(new AppointmentDto(
//                3,
//                103,
//                202,
//                11,
//                "Evenings on Friday.",
//                LocalDate.parse("2025-06-10"),
//                LocalTime.parse("10:00"),
//                "upcoming"
//        ));
    }

    public AppointmentDto MapToDto(Appointment appointment) {
        return new AppointmentDto(appointment.getAid(), appointment.getPatient(), appointment.getDoctor(), appointment.getRoom(), appointment.getNote(), appointment.getDate(), appointment.getTime(), appointment.getStatus());
    }

    public List<AppointmentDto> getAppointments(Integer uid) {
        List<Appointment> appointments = appointmentsRepository.findByPatientAndStatusIn(uid, List.of("upcoming", "accepted"));

        if (appointments.isEmpty()) {
            throw new RuntimeException("No patient appointments found");
        }

        return appointments.stream()
                .map(this::MapToDto)
                .collect(Collectors.toList());
    }

    public AppointmentDto getSingleAppointment(Integer aid) {
        Appointment singleAppointment = appointmentsRepository.findById(aid).orElse(null);
        if(singleAppointment != null) {
            return MapToDto(singleAppointment);
        }
        throw new RuntimeException("No appointment found");
    }

    public AppointmentDto bookAppointment(AppointmentDto newAppointment, Integer uid) {
        Appointment appointment = new Appointment();
        appointment.setPatient(uid);
        appointment.setDoctor(newAppointment.getDoctor());
        appointment.setRoom(null);
        appointment.setNote(newAppointment.getNote());
        appointment.setDate(null);
        appointment.setTime(null);
        appointment.setStatus("upcoming");
        appointmentsRepository.save(appointment);

        AppointmentDto result = new AppointmentDto(appointment.getAid(), appointment.getPatient(), appointment.getDoctor(), appointment.getRoom(), appointment.getNote(), appointment.getDate(), appointment.getTime(), appointment.getStatus());

        return result;
    }

    public AppointmentDto cancelAppointment(Integer aid) {
        Optional<Appointment> optional = appointmentsRepository.findById(aid);
        if(optional.isPresent()) {
            Appointment appointment = optional.get();
            appointment.setStatus("cancelled");
            appointmentsRepository.save(appointment);
            return MapToDto(appointment);
        }
        throw new RuntimeException("Appointment not found");

    }

}
