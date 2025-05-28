package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<UserDto> users = new ArrayList<>();
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        users.add(new UserDto(
                123,
                "John Doe",
                "john.doe@gmail.com",
                "password123",
                "+387 33 975 002",
                "Hrasnička cesta 3a",
                "Sarajevo",
                LocalDate.parse("1985-01-01"),
                "Male",
                "A+",
                "patient",
                "1105993710028"
        ));

        users.add(new UserDto(
                456,
                "Jane Smith",
                "jane.smith@hospital.com",
                "password456",
                "+387 33 975 003",
                "Medical Center St 1",
                "Sarajevo",
                LocalDate.parse("1980-05-15"),
                "Female",
                "B+",
                "doctor",
                "2304006715036"
        ));
    }

    public UserDto getUser(Integer uid) {
        return userRepository.findById(uid)
                .map(User -> convertToDto(User))
                .orElse(null);
    }

    private UserDto convertToDto(User user) {
        return new UserDto(
                user.getUid(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getAddress(),
                user.getCity(),
                user.getDob(),
                user.getGender(),
                user.getBlood_group(),
                user.getRole(),
                user.getJmbg()
        );
    }

    public UserDto updateUser(Integer uid, UserDto userDto) {
        return userRepository.findById(uid)
                .map(existingUser -> {
                    existingUser.setEmail(userDto.getEmail());
                    existingUser.setPhone(userDto.getPhone());
                    existingUser.setAddress(userDto.getAddress());
                    existingUser.setCity(userDto.getCity());
                    existingUser.setDob(userDto.getDob());
                    existingUser.setGender(userDto.getGender());
                    existingUser.setBlood_group(userDto.getBlood_group());

                    userRepository.save(existingUser);

                    return convertToDto(existingUser);
                })
                .orElse(null);
    }
}
