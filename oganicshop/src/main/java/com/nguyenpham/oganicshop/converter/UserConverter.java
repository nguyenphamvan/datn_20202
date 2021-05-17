package com.nguyenpham.oganicshop.converter;

import com.nguyenpham.oganicshop.dto.UserRequest;
import com.nguyenpham.oganicshop.dto.UserResponse;
import com.nguyenpham.oganicshop.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements GeneralConverter<User, UserRequest, UserResponse> {
    @Override
    public UserResponse entityToDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setRole(user.getRole().replace("ROLE_", ""));
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setFullName(user.getFullName());
        userResponse.setPhone(user.getPhone());
        userResponse.setBirthday(user.getBirthday());
        userResponse.setGender(user.getGender());
        userResponse.setCreatedDate(user.getCreatedAt());
        userResponse.setEnabled(user.isEnabled());
        userResponse.setBlocked(user.isBlocked());
        return userResponse;
    }

    @Override
    public User dtoToEntity(UserRequest d) {
        return null;
    }
}
