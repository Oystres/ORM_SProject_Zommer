package com.example.lms.web;

import com.example.lms.dto.SubmissionDto;
import com.example.lms.dto.SubmissionRequest;
import com.example.lms.mapper.SubmissionMapper;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;
import com.example.lms.repository.AssignmentRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.SubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public SubmissionController(SubmissionService submissionService, AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.submissionService = submissionService;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<SubmissionDto> findAll() { return submissionService.findAll().stream().map(SubmissionMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public SubmissionDto getById(@PathVariable Long id) { return SubmissionMapper.toDto(submissionService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubmissionDto create(@Valid @RequestBody SubmissionRequest request) {
        Assignment assignment = assignmentRepository.findById(request.getAssignmentId()).orElseThrow();
        User student = userRepository.findById(request.getStudentId()).orElseThrow();
        Submission created = submissionService.create(SubmissionMapper.fromRequest(request, assignment, student));
        return SubmissionMapper.toDto(created);
    }

    @PatchMapping("/{id}/grade")
    public SubmissionDto grade(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Integer score = body.get("score") != null ? (Integer) body.get("score") : null;
        String feedback = body.get("feedback") != null ? body.get("feedback").toString() : null;
        return SubmissionMapper.toDto(submissionService.grade(id, score, feedback));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { submissionService.delete(id); }
}


