package com.example.lms.web;

import com.example.lms.dto.AnswerOptionDto;
import com.example.lms.dto.AnswerOptionRequest;
import com.example.lms.dto.QuestionDto;
import com.example.lms.dto.QuestionRequest;
import com.example.lms.mapper.AnswerOptionMapper;
import com.example.lms.mapper.QuestionMapper;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.model.Quiz;
import com.example.lms.repository.QuestionRepository;
import com.example.lms.repository.QuizRepository;
import com.example.lms.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuestionController(QuestionService questionService, QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.questionService = questionService;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<QuestionDto> findAll() { return questionService.findAll().stream().map(QuestionMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public QuestionDto getById(@PathVariable Long id) { return QuestionMapper.toDto(questionService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto create(@Valid @RequestBody QuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElseThrow();
        Question created = questionService.create(QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public QuestionDto update(@PathVariable Long id, @Valid @RequestBody QuestionRequest request) {
        Quiz quiz = quizRepository.findById(request.getQuizId()).orElseThrow();
        Question updated = questionService.update(id, QuestionMapper.fromRequest(request, quiz));
        return QuestionMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { questionService.delete(id); }

    @PostMapping("/{id}/options")
    @ResponseStatus(HttpStatus.CREATED)
    public AnswerOptionDto addOption(@PathVariable Long id, @Valid @RequestBody AnswerOptionRequest request) {
        Question question = questionRepository.findById(id).orElseThrow();
        AnswerOption saved = questionService.addOption(AnswerOptionMapper.fromRequest(request, question));
        return AnswerOptionMapper.toDto(saved);
    }

    @DeleteMapping("/options/{optionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOption(@PathVariable Long optionId) { questionService.deleteOption(optionId); }
}


