package com.healthcare.system.healthcare.config;

import com.healthcare.system.healthcare.models.entities.User;
import com.healthcare.system.healthcare.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class StartupSeeder {

    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("dr@house.com").isEmpty()) {
                User doctor = new User();
                doctor.setName("Doctor House");
                doctor.setEmail("dr@house.com");
                doctor.setPassword(passwordEncoder.encode("password123"));
                doctor.setRole("doctor");
                doctor.setPhone("061234567");
                doctor.setAddress("Hospital Lane 1");
                doctor.setCity("Princeton");
                doctor.setDob(LocalDate.of(1970, 6, 1));
                doctor.setGender("male");
                doctor.setBlood_group("AB+");
                doctor.setJmbg("1234567890123");

                userRepository.save(doctor);
            }


        };
    }
}
