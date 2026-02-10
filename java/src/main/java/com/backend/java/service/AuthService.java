package com.backend.java.service;

import com.backend.java.model.dto.login.LoginRequestDto;
import com.backend.java.model.dto.login.LoginResponseDto;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto requestDto);

    LoginResponseDto login(LoginRequestDto requestDto);
}
