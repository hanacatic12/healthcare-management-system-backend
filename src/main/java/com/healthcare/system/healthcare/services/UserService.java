package com.healthcare.system.healthcare.services;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.models.entities.Doctor;
import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.DepartmentsRepository;
import com.healthcare.system.healthcare.repositories.DoctorRepository;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<UserDto> users = new ArrayList<>();
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
//
//    public AuthResponse RegisterUser(RegisterRequest request) {
//        if(!request.getPassword().equals(request.getConfirmPassword())) {
//            throw new RuntimeException("Passwords do not match");
//        } else if (request.getPassword().length() < 8) {
//            throw new RuntimeException("Password must be at least 8 characters");
//        }
//        else {
//            User user = new User();
//            user.setName(request.getFirstName() + " " + request.getLastName());
//            user.setEmail(request.getEmail());
//            user.setPassword(request.getPassword());
//            user.setPhone(request.getPhone());
//            user.setAddress(request.getAddress());
//            user.setCity(request.getCity());
//            user.setDob(request.getDob());
//            user.setGender(request.getGender());
//            user.setBlood_group(request.getBlood_group());
//            userRepository.save(user);
//            return
//        }
//    }

