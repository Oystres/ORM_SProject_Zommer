package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionDto;
import com.example.lms.dto.QuestionDto;
import com.example.lms.dto.QuestionRequest;
import com.example.lms.model.*;

import java.util.stream.Collectors;

public class QuestionMapper {
    public static QuestionDto toDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setQuizId(q.getQuiz() != null ? q.getQuiz().getId() : null);
        dto.setText(q.getText());
        dto.setType(q.getType());
        if (q.getOptions() != null) {
            dto.setOptions(q.getOptions().stream().map(o -> {
                AnswerOptionDto od = new AnswerOptionDto();
                od.setId(o.getId());
                od.setQuestionId(q.getId());
                od.setText(o.getText());
                od.setCorrect(o.isCorrect());
                return od;
            }).collect(Collectors.toList()));
        }
        return dto;
    }

    public static Question fromRequest(QuestionRequest request, Quiz quiz) {
        Question q = new Question();
        q.setQuiz(quiz);
        q.setText(request.getText());
        q.setType(request.getType());
        return q;
    }
}


