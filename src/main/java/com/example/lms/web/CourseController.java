package com.example.lms.web;

import com.example.lms.dto.CourseDto;
import com.example.lms.dto.CourseRequest;
import com.example.lms.mapper.CourseMapper;
import com.example.lms.model.*;
import com.example.lms.repository.CategoryRepository;
import com.example.lms.repository.TagRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public CourseController(CourseService courseService, UserRepository userRepository, CategoryRepository categoryRepository, TagRepository tagRepository) {
        this.courseService = courseService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<CourseDto> findAll() { return courseService.findAll().stream().map(CourseMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public CourseDto getById(@PathVariable Long id) { return CourseMapper.toDto(courseService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDto create(@Valid @RequestBody CourseRequest request) {
        Category category = request.getCategoryId() == null ? null : categoryRepository.findById(request.getCategoryId()).orElse(null);
        User teacher = userRepository.findById(request.getTeacherId()).orElseThrow();
        Set<Tag> tags = Optional.ofNullable(request.getTagIds()).orElseGet(Collections::emptySet).stream()
                .map(id -> tagRepository.findById(id).orElseThrow())
                .collect(Collectors.toSet());
        Course created = courseService.create(CourseMapper.fromRequest(request, category, teacher, tags));
        return CourseMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public CourseDto update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        Course existing = courseService.getById(id);
        Category category = request.getCategoryId() == null ? null : categoryRepository.findById(request.getCategoryId()).orElse(null);
        User teacher = userRepository.findById(request.getTeacherId()).orElseThrow();
        Set<Tag> tags = Optional.ofNullable(request.getTagIds()).orElseGet(Collections::emptySet).stream()
                .map(tid -> tagRepository.findById(tid).orElseThrow())
                .collect(Collectors.toSet());
        CourseMapper.updateEntity(existing, request, category, teacher, tags);
        return CourseMapper.toDto(courseService.update(id, existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { courseService.delete(id); }
}


