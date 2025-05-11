package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.AppointmentDto;
import com.healthcare.system.healthcare.models.DoctorAppointmentsView;
import com.healthcare.system.healthcare.services.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/appointments/{uid}")
@RestController
public class DocAppointmentsController {
    private final AppointmentService appointmentService;

    public DocAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public DoctorAppointmentsView getAppointments(@PathVariable Integer uid, @RequestParam String role) {
        if ("doctor".equalsIgnoreCase(role)) {
            return appointmentService.getDoctorAppointments(uid);
        }
        return null;
    }

    @GetMapping("/single")
    public AppointmentDto getAppointmentById(
            @PathVariable Integer uid,
            @RequestParam Integer aid,
            @RequestParam String role
    ) {
        if ("doctor".equalsIgnoreCase(role)) {
            return appointmentService.getAppointmentById(uid, aid);
        }
        return null;
    }

    @PutMapping
    public AppointmentDto updateAppointment(
            @RequestBody AppointmentDto appointment,
            @RequestParam String action) {
        return appointmentService.updateAppointment(appointment, action);

    }

}
