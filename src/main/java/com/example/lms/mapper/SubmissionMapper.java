package com.example.lms.mapper;

import com.example.lms.dto.SubmissionDto;
import com.example.lms.dto.SubmissionRequest;
import com.example.lms.model.Assignment;
import com.example.lms.model.Submission;
import com.example.lms.model.User;

public class SubmissionMapper {
    public static SubmissionDto toDto(Submission s) {
        SubmissionDto dto = new SubmissionDto();
        dto.setId(s.getId());
        dto.setAssignmentId(s.getAssignment() != null ? s.getAssignment().getId() : null);
        dto.setStudentId(s.getStudent() != null ? s.getStudent().getId() : null);
        dto.setSubmittedAt(s.getSubmittedAt());
        dto.setContent(s.getContent());
        dto.setScore(s.getScore());
        dto.setFeedback(s.getFeedback());
        return dto;
    }

    public static Submission fromRequest(SubmissionRequest request, Assignment assignment, User student) {
        Submission s = new Submission();
        s.setAssignment(assignment);
        s.setStudent(student);
        s.setContent(request.getContent());
        return s;
    }
}


