package com.example.lms.web;

import com.example.lms.dto.UserDto;
import com.example.lms.dto.UserRequest;
import com.example.lms.mapper.UserMapper;
import com.example.lms.model.User;
import com.example.lms.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> findAll() { return userService.findAll().stream().map(UserMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public UserDto getById(@PathVariable Long id) { return UserMapper.toDto(userService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@Valid @RequestBody UserRequest request) { User created = userService.create(UserMapper.fromRequest(request)); return UserMapper.toDto(created); }

    @PutMapping("/{id}")
    public UserDto update(@PathVariable Long id, @Valid @RequestBody UserRequest request) { User updated = userService.update(id, UserMapper.fromRequest(request)); return UserMapper.toDto(updated); }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { userService.delete(id); }
}


