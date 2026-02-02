package com.example.lms.mapper;

import com.example.lms.dto.EnrollmentDto;
import com.example.lms.dto.EnrollmentRequest;
import com.example.lms.model.Course;
import com.example.lms.model.Enrollment;
import com.example.lms.model.User;

public class EnrollmentMapper {
    public static EnrollmentDto toDto(Enrollment e) {
        EnrollmentDto dto = new EnrollmentDto();
        dto.setId(e.getId());
        dto.setStudentId(e.getStudent() != null ? e.getStudent().getId() : null);
        dto.setCourseId(e.getCourse() != null ? e.getCourse().getId() : null);
        dto.setEnrollDate(e.getEnrollDate());
        dto.setStatus(e.getStatus());
        return dto;
    }

    public static Enrollment fromRequest(EnrollmentRequest request, User student, Course course) {
        Enrollment e = new Enrollment();
        e.setStudent(student);
        e.setCourse(course);
        if (request.getEnrollDate() != null) e.setEnrollDate(request.getEnrollDate());
        if (request.getStatus() != null) e.setStatus(request.getStatus());
        return e;
    }
}


