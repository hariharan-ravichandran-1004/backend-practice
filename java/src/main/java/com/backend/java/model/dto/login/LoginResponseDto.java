package com.backend.java.model.dto.login;
import com.backend.java.model.dto.user.UserResponseDto;

public class LoginResponseDto {

    private String message;
    private UserResponseDto user;
    private String token;

    public LoginResponseDto(String message, UserResponseDto user,String token) {
        this.message = message;
        this.user = user;
        this.token = token;
    }

    public String getMessage() { return message; }

    public UserResponseDto getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}