package com.healthcare.system.healthcare.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DepartmentDto {
    private int id;
    private String name;
    private int floor;
}
