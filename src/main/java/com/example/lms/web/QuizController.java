package com.example.lms.web;

import com.example.lms.dto.QuizDto;
import com.example.lms.dto.QuizRequest;
import com.example.lms.mapper.QuizMapper;
import com.example.lms.model.Module;
import com.example.lms.model.Quiz;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final ModuleRepository moduleRepository;

    public QuizController(QuizService quizService, ModuleRepository moduleRepository) {
        this.quizService = quizService;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public List<QuizDto> findAll() { return quizService.findAll().stream().map(QuizMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public QuizDto getById(@PathVariable Long id) { return QuizMapper.toDto(quizService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuizDto create(@Valid @RequestBody QuizRequest request) {
        Module module = moduleRepository.findById(request.getModuleId()).orElseThrow();
        Quiz created = quizService.create(QuizMapper.fromRequest(request, module));
        return QuizMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public QuizDto update(@PathVariable Long id, @Valid @RequestBody QuizRequest request) {
        Quiz existing = quizService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId()).orElseThrow();
        QuizMapper.updateEntity(existing, request, module);
        return QuizMapper.toDto(quizService.update(id, existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { quizService.delete(id); }
}


