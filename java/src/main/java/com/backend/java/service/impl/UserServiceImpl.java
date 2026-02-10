package com.backend.java.service.impl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.model.entity.UserEntity;
import com.backend.java.repository.UserRepository;
@Service
public class UserServiceImpl {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository repository,
                           PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponseDto createUser(RegisterRequestDto dto) {

        // ✅ Check if email already exists
        if (repository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email is already registered");
        }

        // Create new user entity
        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        // 🔐 Hash the password
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Save user
        repository.save(user);

        //response DTO
        RegisterResponseDto response = new RegisterResponseDto();
        return response;
    }

 
}
