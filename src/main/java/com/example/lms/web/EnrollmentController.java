package com.example.lms.web;

import com.example.lms.dto.EnrollmentDto;
import com.example.lms.dto.EnrollmentRequest;
import com.example.lms.mapper.EnrollmentMapper;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentService enrollmentService, UserRepository userRepository, CourseRepository courseRepository) {
        this.enrollmentService = enrollmentService;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<EnrollmentDto> findAll() { return enrollmentService.findAll().stream().map(EnrollmentMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public EnrollmentDto getById(@PathVariable Long id) { return EnrollmentMapper.toDto(enrollmentService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnrollmentDto create(@Valid @RequestBody EnrollmentRequest request) {
        User student = userRepository.findById(request.getStudentId()).orElseThrow();
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
        Enrollment created = enrollmentService.create(EnrollmentMapper.fromRequest(request, student, course));
        return EnrollmentMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public EnrollmentDto update(@PathVariable Long id, @Valid @RequestBody EnrollmentRequest request) {
        Enrollment existing = enrollmentService.getById(id);
        User student = userRepository.findById(request.getStudentId()).orElseThrow();
        Course course = courseRepository.findById(request.getCourseId()).orElseThrow();
        Enrollment updated = enrollmentService.update(id, EnrollmentMapper.fromRequest(request, student, course));
        return EnrollmentMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { enrollmentService.delete(id); }
}


