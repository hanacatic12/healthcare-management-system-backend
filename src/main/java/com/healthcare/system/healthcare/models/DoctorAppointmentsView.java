package com.healthcare.system.healthcare.models;

import com.healthcare.system.healthcare.models.dtos.AppointmentDto;
import lombok.Getter;

import java.util.List;

@Getter
public class DoctorAppointmentsView {
    private List<AppointmentDto> requested;
    private List<AppointmentDto> upcoming;

    public DoctorAppointmentsView(List<AppointmentDto> requested, List<AppointmentDto> upcoming) {
        this.requested = requested;
        this.upcoming = upcoming;
    }
}
