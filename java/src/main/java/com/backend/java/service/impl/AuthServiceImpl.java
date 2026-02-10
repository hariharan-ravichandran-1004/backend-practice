package com.backend.java.service.impl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.java.mapper.UserMapper;
import com.backend.java.model.dto.login.LoginRequestDto;
import com.backend.java.model.dto.login.LoginResponseDto;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.model.entity.UserEntity;
import com.backend.java.repository.UserRepository;
import com.backend.java.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public RegisterResponseDto register(RegisterRequestDto requestDto) {

        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        return userMapper.toRegisterResponse(savedUser);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {

        UserEntity user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return userMapper.toLoginResponse(user);
    }
}
