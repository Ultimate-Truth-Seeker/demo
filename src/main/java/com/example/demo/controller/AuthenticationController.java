package com.example.demo.controller;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.dto.ApiResponseDto;
import com.example.demo.model.dto.SignInRequestDto;
import com.example.demo.model.dto.SignUpRequestDto;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseDto<?>> signUpUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto)
             throws UserAlreadyExistsException {
        return authService.signUpUser(signUpRequestDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponseDto<?>> signInUser(@RequestBody @Valid SignInRequestDto signInRequestDto){
        return authService.signInUser(signInRequestDto);
    }

}