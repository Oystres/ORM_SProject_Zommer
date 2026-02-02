package com.example.lms.mapper;

import com.example.lms.dto.AssignmentDto;
import com.example.lms.dto.AssignmentRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Lesson;

public class AssignmentMapper {
    public static AssignmentDto toDto(Assignment a) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(a.getId());
        dto.setLessonId(a.getLesson() != null ? a.getLesson().getId() : null);
        dto.setTitle(a.getTitle());
        dto.setDescription(a.getDescription());
        dto.setDueDate(a.getDueDate());
        dto.setMaxScore(a.getMaxScore());
        return dto;
    }

    public static Assignment fromRequest(AssignmentRequest request, Lesson lesson) {
        Assignment a = new Assignment();
        a.setLesson(lesson);
        a.setTitle(request.getTitle());
        a.setDescription(request.getDescription());
        a.setDueDate(request.getDueDate());
        a.setMaxScore(request.getMaxScore());
        return a;
    }

    public static void updateEntity(Assignment existing, AssignmentRequest request, Lesson lesson) {
        existing.setLesson(lesson);
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setDueDate(request.getDueDate());
        existing.setMaxScore(request.getMaxScore());
    }
}


