package com.healthcare.system.healthcare.controllers;

import com.healthcare.system.healthcare.models.dtos.UserDto;
import com.healthcare.system.healthcare.services.UserService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class ProfileController {
    private UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{uid}")
    public UserDto getUserProfile(@PathVariable Integer uid) {
        return userService.getUser(uid);
    }

    @PutMapping("/{uid}")
    public UserDto updateUserProfile(@PathVariable Integer uid,
                                     @RequestBody UserDto user) {
        return userService.updateUser(uid, user);
    }

}
