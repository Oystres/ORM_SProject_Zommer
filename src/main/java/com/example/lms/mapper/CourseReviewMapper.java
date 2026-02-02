package com.example.lms.mapper;

import com.example.lms.dto.CourseReviewDto;
import com.example.lms.dto.CourseReviewRequest;
import com.example.lms.model.Course;
import com.example.lms.model.CourseReview;
import com.example.lms.model.User;

public class CourseReviewMapper {
    public static CourseReviewDto toDto(CourseReview r) {
        CourseReviewDto dto = new CourseReviewDto();
        dto.setId(r.getId());
        dto.setCourseId(r.getCourse() != null ? r.getCourse().getId() : null);
        dto.setStudentId(r.getStudent() != null ? r.getStudent().getId() : null);
        dto.setRating(r.getRating());
        dto.setComment(r.getComment());
        dto.setCreatedAt(r.getCreatedAt());
        return dto;
    }

    public static CourseReview fromRequest(CourseReviewRequest request, Course course, User student) {
        CourseReview r = new CourseReview();
        r.setCourse(course);
        r.setStudent(student);
        r.setRating(request.getRating());
        r.setComment(request.getComment());
        return r;
    }
}


