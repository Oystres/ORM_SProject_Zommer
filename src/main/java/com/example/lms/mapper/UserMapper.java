package com.example.lms.mapper;

import com.example.lms.dto.UserDto;
import com.example.lms.dto.UserRequest;
import com.example.lms.model.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    public static User fromRequest(UserRequest request) {
        User u = new User();
        u.setName(request.getName());
        u.setEmail(request.getEmail());
        u.setRole(request.getRole());
        return u;
    }
}


