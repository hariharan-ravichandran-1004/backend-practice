package com.backend.java.service.impl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.java.exception.CustomExceptions;
import com.backend.java.exception.CustomExceptions.InvalidCredentialsException;
import com.backend.java.mapper.UserMapper;
import com.backend.java.model.dto.login.LoginRequestDto;
import com.backend.java.model.dto.login.LoginResponseDto;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.model.entity.UserEntity;
import com.backend.java.repository.UserRepository;
import com.backend.java.service.AuthService;
import com.backend.java.utils.JwtUtils;

@Service
public class AuthServiceImpl implements AuthService {
 private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper,
                           AuthenticationManager authenticationManager,
                           JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

  @Override
public RegisterResponseDto register(RegisterRequestDto requestDto) {
    String name = requestDto.getName();
    String email = requestDto.getEmail();
    String password = requestDto.getPassword();
    // Check if user already exists
    if (userRepository.existsByEmail(email)) {
        System.out.println("User already exists: " + email);
        UserEntity existingUser = userRepository.findByEmail(email).get();
        return userMapper.toRegisterResponse(existingUser);
    }

    // Create user entity
    UserEntity user = new UserEntity();
    user.setName(name);
    user.setEmail(email);
    String encodedPassword = passwordEncoder.encode(password);
    System.out.println("Encoded Password: " + encodedPassword);
    user.setPassword(encodedPassword);

    UserEntity savedUser = userRepository.save(user);
    System.out.println("User registered with email: " + savedUser.getEmail());
    return userMapper.toRegisterResponse(savedUser);
}

@Override
public LoginResponseDto login(LoginRequestDto requestDto) {
      String email = requestDto.getEmail();
    String password = requestDto.getPassword();

    UserEntity user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

    System.out.println("STEP 1 - Before authenticate");
    System.out.println("Email: [" + email + "]");
    System.out.println("Password length: " + password.length());

    // DEBUG: Manual check
    boolean match = passwordEncoder.matches(password, user.getPassword());
    System.out.println("DEBUG: Manual BCrypt match result: " + match);
    System.out.println("DEBUG: DB Hash: " + user.getPassword());

    try {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                email,
                password
            )
        );
    }catch (org.springframework.security.authentication.BadCredentialsException | 
         org.springframework.security.core.userdetails.UsernameNotFoundException e) {
    // Throw your custom exception, GlobalExceptionHandler will map to 401
    throw new InvalidCredentialsException("Invalid email or password");
}

    System.out.println("STEP 2 - After authenticate");

    String token = jwtUtils.generateToken(user.getEmail());
    System.out.println("STEP 3 - Token generated: " + token);

    return userMapper.toLoginResponse(user, token);
}

}
