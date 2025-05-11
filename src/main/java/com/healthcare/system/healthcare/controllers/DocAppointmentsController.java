package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.AppointmentDto;
import com.healthcare.system.healthcare.models.DoctorAppointmentsView;
import com.healthcare.system.healthcare.services.AppointmentService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/appointments")
@RestController
public class DocAppointmentsController {
    private final AppointmentService appointmentService;

    public DocAppointmentsController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/{uid}")
    public DoctorAppointmentsView getAppointments(@PathVariable Integer uid, @RequestParam String role) {
        if ("doctor".equalsIgnoreCase(role)) {
            return appointmentService.getDoctorAppointments(uid);
        }
        return null;
    }

    @PutMapping("/{uid}")
    public AppointmentDto updateAppointment(
            @RequestBody AppointmentDto appointment,
            @RequestParam String action) {
        return appointmentService.updateAppointment(appointment, action);

    }

}
