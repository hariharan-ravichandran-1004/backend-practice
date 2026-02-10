package com.backend.java.controller;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserServiceImpl serviceImpl;

    public UserController(UserServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

   @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> create(@Valid @RequestBody RegisterRequestDto request) {
        RegisterResponseDto response = serviceImpl.createUser(request);
        return ResponseEntity.ok(response); // HTTP 200
    }
    
}
