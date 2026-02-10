package com.backend.java.model.dto.login;
import com.backend.java.model.dto.user.UserResponseDto;

public class LoginResponseDto {

    private String message;
    private UserResponseDto user;

    public LoginResponseDto(String message, UserResponseDto user) {
        this.message = message;
        this.user = user;
    }

    public String getMessage() { return message; }
    public UserResponseDto getUser() { return user; }
}