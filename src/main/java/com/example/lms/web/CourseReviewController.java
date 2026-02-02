package com.example.lms.web;

import com.example.lms.dto.CourseReviewDto;
import com.example.lms.dto.CourseReviewRequest;
import com.example.lms.mapper.CourseReviewMapper;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.CourseReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reviews")
public class CourseReviewController {

    private final CourseReviewService courseReviewService;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public CourseReviewController(CourseReviewService courseReviewService, CourseRepository courseRepository, UserRepository userRepository) {
        this.courseReviewService = courseReviewService;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<CourseReviewDto> findAll() { return courseReviewService.findAll().stream().map(CourseReviewMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public CourseReviewDto getById(@PathVariable Long id) { return CourseReviewMapper.toDto(courseReviewService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseReviewDto create(@Valid @RequestBody CourseReviewRequest request) {
        Course c = courseRepository.findById(request.getCourseId()).orElseThrow();
        User s = userRepository.findById(request.getStudentId()).orElseThrow();
        CourseReview created = courseReviewService.create(CourseReviewMapper.fromRequest(request, c, s));
        return CourseReviewMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public CourseReviewDto update(@PathVariable Long id, @Valid @RequestBody CourseReviewRequest request) {
        Course c = courseRepository.findById(request.getCourseId()).orElseThrow();
        User s = userRepository.findById(request.getStudentId()).orElseThrow();
        CourseReview updated = courseReviewService.update(id, CourseReviewMapper.fromRequest(request, c, s));
        return CourseReviewMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { courseReviewService.delete(id); }
}


