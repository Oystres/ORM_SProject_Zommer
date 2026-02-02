package com.example.lms.web;

import com.example.lms.dto.AssignmentDto;
import com.example.lms.dto.AssignmentRequest;
import com.example.lms.mapper.AssignmentMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import com.example.lms.service.AssignmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final LessonRepository lessonRepository;

    public AssignmentController(AssignmentService assignmentService, LessonRepository lessonRepository) {
        this.assignmentService = assignmentService;
        this.lessonRepository = lessonRepository;
    }

    @GetMapping
    public List<AssignmentDto> findAll() { return assignmentService.findAll().stream().map(AssignmentMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public AssignmentDto getById(@PathVariable Long id) { return AssignmentMapper.toDto(assignmentService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AssignmentDto create(@Valid @RequestBody AssignmentRequest request) {
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow();
        Assignment created = assignmentService.create(AssignmentMapper.fromRequest(request, lesson));
        return AssignmentMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public AssignmentDto update(@PathVariable Long id, @Valid @RequestBody AssignmentRequest request) {
        Assignment existing = assignmentService.getById(id);
        Lesson lesson = lessonRepository.findById(request.getLessonId()).orElseThrow();
        AssignmentMapper.updateEntity(existing, request, lesson);
        return AssignmentMapper.toDto(assignmentService.update(id, existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { assignmentService.delete(id); }
}


