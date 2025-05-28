package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.models.DoctorAppointmentsView;
import com.healthcare.system.healthcare.repositories.AppointmentsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private List<AppointmentDto> appointments = new ArrayList<>();

    private AppointmentsRepository appointmentsRepository;

    public AppointmentService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
        appointments.add(new AppointmentDto(
                1,
                123,
                456,
                0,
                "Available on Monday.",
                LocalDate.parse("2025-06-10"),
                LocalTime.parse("10:00"),
                "upcoming"
        ));

        appointments.add(new AppointmentDto(
                2,
                123,
                456,
                22,
                "Afternoons on Thursday.",
                LocalDate.parse("2025-06-10"),
                LocalTime.parse("10:00"),
                "accepted"
        ));

        appointments.add(new AppointmentDto(
                3,
                103,
                202,
                11,
                "Evenings on Friday.",
                LocalDate.parse("2025-06-10"),
                LocalTime.parse("10:00"),
                "upcoming"
        ));
    }

    public DoctorAppointmentsView getDoctorAppointments(Integer uid) {
        List<AppointmentDto> requested = appointments.stream()
                .filter(a -> a.getDoctorId().equals(uid) && "upcoming".equalsIgnoreCase(a.getStatus()))
                .collect(Collectors.toList());

        List<AppointmentDto> upcoming = appointments.stream()
                .filter(a -> a.getDoctorId().equals(uid) && "accepted".equalsIgnoreCase(a.getStatus()))
                .collect(Collectors.toList());

        return new DoctorAppointmentsView(requested, upcoming);
    }

    public AppointmentDto updateAppointment(AppointmentDto appointment, String action) {
        AppointmentDto toUpdate = appointments.stream()
                .filter(p -> appointment.getAid() != null && appointment.getAid().equals(p.getAid()))
                .findFirst()
                .orElse(null);

        if (toUpdate != null && "accept".equalsIgnoreCase(action)) {
            if (appointment.getDate() == null || appointment.getTime() == null || appointment.getRoom() == 0) {
                throw new IllegalArgumentException("Date, time, and room must be provided when accepting an appointment.");
            }

            toUpdate.setDate(appointment.getDate());
            toUpdate.setTime(appointment.getTime());
            toUpdate.setRoom(appointment.getRoom());
            toUpdate.setStatus("accepted");
        }
        else if ("reject".equalsIgnoreCase(action))
            toUpdate.setStatus("rejected");

        return toUpdate;
    }

    public AppointmentDto getAppointmentById(Integer uid, Integer aid) {
        return appointments.stream()
                .filter(a -> a.getDoctorId().equals(uid) && a.getAid().equals(aid))
                .findFirst()
                .orElse(null);
    }

}
