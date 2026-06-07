package com.auth.controller;

import com.auth.exception.BadRequestException;
import com.auth.model.JwtTokenResponse;
import com.auth.model.LoginRequest;
import com.auth.model.User;
import com.auth.model.UserDto;
import com.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register-user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto registerUser(@RequestBody User user){
        UserDto newUserDto = userService.saveUser(user);
        return newUserDto;
    }

    @PostMapping("/generate-token")
    @ResponseStatus(HttpStatus.CREATED)
    public JwtTokenResponse generateToken(@RequestBody LoginRequest loginRequest){


     Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
     if(authentication.isAuthenticated()){
         return userService.generateToken(loginRequest.getUsername());
     }else{
         throw new BadRequestException("Invalid username or password");
     }




    }
}
