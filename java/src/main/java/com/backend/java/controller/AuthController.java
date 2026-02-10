package com.backend.java.controller;
import com.backend.java.model.dto.login.LoginRequestDto;
import com.backend.java.model.dto.login.LoginResponseDto;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.service.impl.AuthServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    public AuthController(AuthServiceImpl authServiceImpl) {
        this.authServiceImpl = authServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto requestDto) {
        authServiceImpl.register(requestDto);
        RegisterResponseDto response = new RegisterResponseDto("User created successfully");
        return ResponseEntity.ok(response);
    }

     @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response =  authServiceImpl.login(requestDto);
        return ResponseEntity.ok(response);
    }
    
    
}
