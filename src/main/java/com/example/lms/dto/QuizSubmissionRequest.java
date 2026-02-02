package com.example.lms.dto;

import jakarta.validation.constraints.NotNull;

public class QuizSubmissionRequest {
    @NotNull
    private Long quizId;
    @NotNull
    private Long studentId;
    private Integer score;

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
}


