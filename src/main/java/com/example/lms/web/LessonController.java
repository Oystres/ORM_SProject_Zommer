package com.example.lms.web;

import com.example.lms.dto.LessonDto;
import com.example.lms.dto.LessonRequest;
import com.example.lms.mapper.LessonMapper;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;
    private final ModuleRepository moduleRepository;

    public LessonController(LessonService lessonService, ModuleRepository moduleRepository) {
        this.lessonService = lessonService;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping
    public List<LessonDto> findAll() { return lessonService.findAll().stream().map(LessonMapper::toDto).collect(Collectors.toList()); }

    @GetMapping("/{id}")
    public LessonDto getById(@PathVariable Long id) { return LessonMapper.toDto(lessonService.getById(id)); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LessonDto create(@Valid @RequestBody LessonRequest request) {
        Module module = moduleRepository.findById(request.getModuleId()).orElseThrow();
        Lesson created = lessonService.create(LessonMapper.fromRequest(request, module));
        return LessonMapper.toDto(created);
    }

    @PutMapping("/{id}")
    public LessonDto update(@PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        Lesson existing = lessonService.getById(id);
        Module module = moduleRepository.findById(request.getModuleId()).orElseThrow();
        LessonMapper.updateEntity(existing, request, module);
        return LessonMapper.toDto(lessonService.update(id, existing));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { lessonService.delete(id); }
}


