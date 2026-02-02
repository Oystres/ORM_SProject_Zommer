package com.example.lms.mapper;

import com.example.lms.dto.QuizDto;
import com.example.lms.dto.QuizRequest;
import com.example.lms.model.Module;
import com.example.lms.model.Quiz;

public class QuizMapper {
    public static QuizDto toDto(Quiz q) {
        QuizDto dto = new QuizDto();
        dto.setId(q.getId());
        dto.setModuleId(q.getModule() != null ? q.getModule().getId() : null);
        dto.setTitle(q.getTitle());
        dto.setTimeLimitMinutes(q.getTimeLimitMinutes());
        return dto;
    }

    public static Quiz fromRequest(QuizRequest request, Module module) {
        Quiz q = new Quiz();
        q.setModule(module);
        q.setTitle(request.getTitle());
        q.setTimeLimitMinutes(request.getTimeLimitMinutes());
        return q;
    }

    public static void updateEntity(Quiz existing, QuizRequest request, Module module) {
        existing.setModule(module);
        existing.setTitle(request.getTitle());
        existing.setTimeLimitMinutes(request.getTimeLimitMinutes());
    }
}


