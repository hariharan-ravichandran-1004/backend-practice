package com.backend.java.mapper;
import org.springframework.stereotype.Component;
import com.backend.java.model.dto.login.LoginResponseDto;
import com.backend.java.model.dto.register.RegisterResponseDto;
import com.backend.java.model.dto.user.UserResponseDto;
import com.backend.java.model.entity.UserEntity;
@Component
public class UserMapper {

    public UserResponseDto toUserResponse(UserEntity user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail()
        );
    }

    public RegisterResponseDto toRegisterResponse(UserEntity user) {
        return new RegisterResponseDto("User created successfully");
    }

    public LoginResponseDto toLoginResponse(UserEntity user) {
        return new LoginResponseDto(
                "Login successful",
                toUserResponse(user)
        );
    }
}
