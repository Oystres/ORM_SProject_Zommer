package com.example.lms.mapper;

import com.example.lms.dto.LessonDto;
import com.example.lms.dto.LessonRequest;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;

public class LessonMapper {
    public static LessonDto toDto(Lesson l) {
        LessonDto dto = new LessonDto();
        dto.setId(l.getId());
        dto.setModuleId(l.getModule() != null ? l.getModule().getId() : null);
        dto.setTitle(l.getTitle());
        dto.setContent(l.getContent());
        dto.setVideoUrl(l.getVideoUrl());
        return dto;
    }

    public static Lesson fromRequest(LessonRequest request, Module module) {
        Lesson l = new Lesson();
        l.setModule(module);
        l.setTitle(request.getTitle());
        l.setContent(request.getContent());
        l.setVideoUrl(request.getVideoUrl());
        return l;
    }

    public static void updateEntity(Lesson existing, LessonRequest request, Module module) {
        existing.setModule(module);
        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        existing.setVideoUrl(request.getVideoUrl());
    }
}


