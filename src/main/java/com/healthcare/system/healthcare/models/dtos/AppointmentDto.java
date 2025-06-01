package com.healthcare.system.healthcare.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
public class  AppointmentDto {
    private Integer aid;
    private Integer patientId;
    private Integer doctorId;
    private Integer room;
    private String note;
    private LocalDate date;
    private LocalTime time;
    private String status;


}
