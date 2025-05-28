package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientAppointmentService {
    private List<AppointmentDto> appointments = new ArrayList<>();
    private Integer counter = 4;

    public PatientAppointmentService() {
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

    public List<AppointmentDto> getAppointments(Integer uid) {
        List<AppointmentDto> patientAppointments = appointments.stream().filter(a -> a.getPatientId().equals(uid) && (a.getStatus().equals("upcoming") || a.getStatus().equals("accepted"))).collect(Collectors.toList());
        if(patientAppointments == null) throw new RuntimeException("No patient appointments found");

        return patientAppointments;
    }

    public AppointmentDto getSingleAppointment(Integer aid) {
        AppointmentDto singleAppointment = appointments.stream().filter(a -> a.getAid().equals(aid)).findFirst().orElse(null);
        return singleAppointment;
    }

    public AppointmentDto bookAppointment(AppointmentDto newAppointment, Integer uid) {
        newAppointment.setAid(counter++);
        newAppointment.setPatientId(uid);

        newAppointment.setRoom(null);
        newAppointment.setDate(null);
        newAppointment.setTime(null);
        newAppointment.setStatus("upcoming");

        appointments.add(newAppointment);

        AppointmentDto last = appointments.get(appointments.size() - 1);
        return last;

    }

    public AppointmentDto cancelAppointment(Integer aid) {
        for(AppointmentDto appointment : appointments) {
            if(appointment.getAid().equals(aid)) {
                appointment.setStatus("cancelled");
                return appointment;
            }
        }
        return null;
    }
}
