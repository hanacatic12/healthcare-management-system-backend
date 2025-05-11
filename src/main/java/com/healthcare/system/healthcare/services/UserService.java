package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<UserDto> users = new ArrayList<>();
    private Integer counter = 0;

    public UserService() {
        users.add(new UserDto(
                counter++,
                "John Doe",
                "john.doe@gmail.com",
                "password123",
                "+387 33 975 002",
                "Hrasnička cesta 3a",
                "Sarajevo",
                LocalDate.parse("1985-01-01"),
                "Male",
                "A+",
                LocalDate.parse("2024-02-15"),
                null,
                null,
                null,
                "patient",
                "1105993710028"
        ));

        users.add(new UserDto(
                counter++,
                "Jane Smith",
                "jane.smith@hospital.com",
                "password456",
                "+387 33 975 003",
                "Medical Center St 1",
                "Sarajevo",
                LocalDate.parse("1980-05-15"),
                "Female",
                "B+",
                null,
                "Cardiology",
                "09:00",
                "17:00",
                "doctor",
                "2304006715036"
        ));
    }

    public UserDto getUser(Integer uid) {
        return users.stream()
                .filter(p -> uid != null && uid.equals(p.getUid()))
                .findFirst()
                .orElse(null);
    }

    public UserDto updateUser(Integer uid, UserDto user) {
        UserDto toUpdate = users.stream()
                .filter(p -> uid != null && uid.equals(p.getUid()))
                .findFirst()
                .orElse(null);

        if (toUpdate != null) {
            toUpdate.setEmail(user.getEmail());
            toUpdate.setPhone(user.getPhone());
            toUpdate.setAddress(user.getAddress());
            toUpdate.setCity(user.getCity());
            toUpdate.setDob(user.getDob());
            toUpdate.setGender(user.getGender());
            toUpdate.setBlood_group(user.getBlood_group());
        }

        return toUpdate;
    }
}
