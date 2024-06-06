package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.DBUser;
import com.example.demo.model.dto.ApiResponseDto;
import com.example.demo.model.dto.SignInRequestDto;
import com.example.demo.model.dto.SignInResponseDto;
import com.example.demo.model.dto.SignUpRequestDto;
import com.example.demo.security.UserDetailsImpl;
import com.example.demo.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthServiceImpl implements AuthService {

    @Autowired
    private DBUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<ApiResponseDto<?>> signUpUser(SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException{
        if (userService.existsByEmail(signUpRequestDto.getEmail())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided email already exists. Try sign in or provide another email.");
        }
        if (userService.existsByUsername(signUpRequestDto.getUsername())) {
            throw new UserAlreadyExistsException("Registration Failed: Provided username already exists. Try sign in or provide another username.");
        }

        DBUser user = createUser(signUpRequestDto);
        userService.guardar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("User account has been successfully created!")
                        .build()
        );
    }

    @Override
    public ResponseEntity<ApiResponseDto<?>> signInUser(SignInRequestDto signInRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getUsername(), signInRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();


        SignInResponseDto signInResponseDto = SignInResponseDto.builder()
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .id( userDetails.getId())
                .token(jwt)
                .type("Bearer")

                .build();

        return ResponseEntity.ok(
                ApiResponseDto.builder()
                        .isSuccess(true)
                        .message("Sign in successfull!")
                        .response(signInResponseDto)
                        .build()
        );
    }

    private DBUser createUser(SignUpRequestDto signUpRequestDto) {
        return DBUser.builder()
                .email(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getUsername())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .build();
    }
}