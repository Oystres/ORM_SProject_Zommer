package com.example.lms.web;

import com.example.lms.dto.QuizSubmissionDto;
import com.example.lms.dto.QuizSubmissionRequest;
import com.example.lms.mapper.QuizSubmissionMapper;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;
import com.example.lms.repository.QuizRepository;
import com.example.lms.repository.UserRepository;
import com.example.lms.service.QuizSubmissionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quiz-submissions")
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizSubmissionController(QuizSubmissionService quizSubmissionService, QuizRepository quizRepository, UserRepository userRepository) {
        this.quizSubmissionService = quizSubmissionService;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public List<QuizSubmissionDto> findAll() { return quizSubmissionService.findAll().stream().map(QuizSubmissionMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public QuizSubmissionDto getById(@PathVariable Long id) { return QuizSubmissionMapper.toDto(quizSubmissionService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizSubmissionDto create(@Valid @RequestBody QuizSubmissionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElseThrow();
        User student = userRepository.findById(request.getStudentId()).orElseThrow();
        QuizSubmission created = quizSubmissionService.create(QuizSubmissionMapper.fromRequest(request, quiz, student));
        return QuizSubmissionMapper.toDto(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { quizSubmissionService.delete(id); }
}


