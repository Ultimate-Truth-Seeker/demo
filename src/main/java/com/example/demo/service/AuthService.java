package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.dto.ApiResponseDto;
import com.example.demo.model.dto.SignInRequestDto;
import com.example.demo.model.dto.SignUpRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException;

    ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto);
}