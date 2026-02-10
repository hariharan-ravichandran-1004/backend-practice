package com.backend.java.mapper;
import com.backend.java.model.dto.register.RegisterRequestDto;
import com.backend.java.model.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(RegisterRequestDto dto) {
        UserEntity user = new UserEntity();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return user;
    }

    // public static UserResponseDto toDto(UserEntity user) {
    //     UserResponseDto dto = new UserResponseDto();
    //     dto.setId(user.getId());
    //     dto.setName(user.getName());
    //     dto.setEmail(user.getEmail());
    //     return dto;
    // }
}
