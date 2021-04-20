package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.UserRequestDto;
import com.nguyenpham.oganicshop.dto.UserResponseDto;
import com.nguyenpham.oganicshop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements GeneralConverter<User, UserRequestDto, UserResponseDto> {
    @Override
    public UserResponseDto entityToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setRole(user.getRole().replace("ROLE_", ""));
        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFullName(user.getFullName());
        userResponseDto.setPhone(user.getPhone());
        userResponseDto.setBirthday(user.getBirthday());
        userResponseDto.setGender(user.getGender());
        userResponseDto.setCreatedDate(user.getCreatedAt());
        userResponseDto.setEnabled(user.isEnabled());
        userResponseDto.setBlocked(user.isBlocked());
        return userResponseDto;
    }

    @Override
    public User dtoToEntity(UserRequestDto d) {
        return null;
    }
}
