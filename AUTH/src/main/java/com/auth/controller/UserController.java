package com.auth.controller;

import com.auth.model.User;
import com.auth.model.UserDto;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody User user){
        UserDto newUserDto = userService.saveUser(user);
        return newUserDto;
    }
}
