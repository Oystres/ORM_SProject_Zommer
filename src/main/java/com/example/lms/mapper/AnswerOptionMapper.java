package com.example.lms.mapper;

import com.example.lms.dto.AnswerOptionDto;
import com.example.lms.dto.AnswerOptionRequest;
import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;

public class AnswerOptionMapper {
    public static AnswerOptionDto toDto(AnswerOption o) {
        AnswerOptionDto dto = new AnswerOptionDto();
        dto.setId(o.getId());
        dto.setQuestionId(o.getQuestion() != null ? o.getQuestion().getId() : null);
        dto.setText(o.getText());
        dto.setCorrect(o.isCorrect());
        return dto;
    }

    public static AnswerOption fromRequest(AnswerOptionRequest request, Question q) {
        AnswerOption o = new AnswerOption();
        o.setQuestion(q);
        o.setText(request.getText());
        o.setCorrect(request.isCorrect());
        return o;
    }
}


