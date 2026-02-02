package com.example.lms.mapper;

import com.example.lms.dto.QuizSubmissionDto;
import com.example.lms.dto.QuizSubmissionRequest;
import com.example.lms.model.Quiz;
import com.example.lms.model.QuizSubmission;
import com.example.lms.model.User;

public class QuizSubmissionMapper {
    public static QuizSubmissionDto toDto(QuizSubmission qs) {
        QuizSubmissionDto dto = new QuizSubmissionDto();
        dto.setId(qs.getId());
        dto.setQuizId(qs.getQuiz() != null ? qs.getQuiz().getId() : null);
        dto.setStudentId(qs.getStudent() != null ? qs.getStudent().getId() : null);
        dto.setScore(qs.getScore());
        dto.setTakenAt(qs.getTakenAt());
        return dto;
    }

    public static QuizSubmission fromRequest(QuizSubmissionRequest request, Quiz quiz, User student) {
        QuizSubmission qs = new QuizSubmission();
        qs.setQuiz(quiz);
        qs.setStudent(student);
        qs.setScore(request.getScore());
        return qs;
    }
}


