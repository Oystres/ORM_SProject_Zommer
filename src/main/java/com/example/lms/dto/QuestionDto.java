package com.example.lms.dto;

import com.example.lms.model.QuestionType;
import java.util.List;

public class QuestionDto {
    private Long id;
    private Long quizId;
    private String text;
    private QuestionType type;
    private List<AnswerOptionDto> options;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public QuestionType getType() { return type; }
    public void setType(QuestionType type) { this.type = type; }
    public List<AnswerOptionDto> getOptions() { return options; }
    public void setOptions(List<AnswerOptionDto> options) { this.options = options; }
}


