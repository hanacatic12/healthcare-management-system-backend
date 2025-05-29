package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import com.healthcare.system.healthcare.services.PatientAppointmentService;
import com.healthcare.system.healthcare.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("appointments/patient/{uid}")
public class PatientAppointmentsController {
    private PatientAppointmentService appointmentService;
    private UserService userService;

    public PatientAppointmentsController(PatientAppointmentService appointmentService) {this.appointmentService = appointmentService;}

    @GetMapping
    public List<AppointmentDto> getAppointments(@PathVariable Integer uid) {
        return appointmentService.getAppointments(uid);
    }

    @GetMapping("/single")
    public AppointmentDto getSingleAppointment(@PathVariable Integer uid, @RequestParam Integer aid) {
        return appointmentService.getSingleAppointment(aid);
    }

    @PostMapping("/schedule")
    public AppointmentDto bookAppointment(@RequestBody AppointmentDto newAppointment, @PathVariable Integer uid) {
        return appointmentService.bookAppointment(newAppointment, uid);
    }

    @PutMapping("cancel")
    public AppointmentDto cancelAppointment(@RequestParam Integer aid) {
        return appointmentService.cancelAppointment(aid);
    }


}
