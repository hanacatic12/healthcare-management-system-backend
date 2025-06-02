package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.DepartmentsRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentsRepository departmentsRepository;


    public UserService(UserRepository userRepository, DoctorRepository doctorRepository, DepartmentsRepository departmentsRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;

        this.departmentsRepository = departmentsRepository;
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