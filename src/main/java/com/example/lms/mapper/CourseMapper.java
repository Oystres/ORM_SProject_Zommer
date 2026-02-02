package com.example.lms.mapper;

import com.example.lms.dto.CourseDto;
import com.example.lms.dto.CourseRequest;
import com.example.lms.model.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

    public static CourseDto toDto(Course course) {
        CourseDto dto = new CourseDto();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setStartDate(course.getStartDate());
        dto.setCategoryId(course.getCategory() != null ? course.getCategory().getId() : null);
        dto.setTeacherId(course.getTeacher() != null ? course.getTeacher().getId() : null);
        Set<Long> tagIds = course.getTags() == null ? new HashSet<>() : course.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        dto.setTagIds(tagIds);
        return dto;
    }

    public static Course fromRequest(CourseRequest request, Category category, User teacher, Set<Tag> tags) {
        Course c = new Course();
        c.setTitle(request.getTitle());
        c.setDescription(request.getDescription());
        c.setDuration(request.getDuration());
        c.setStartDate(request.getStartDate());
        c.setCategory(category);
        c.setTeacher(teacher);
        c.setTags(tags);
        return c;
    }

    public static void updateEntity(Course existing, CourseRequest request, Category category, User teacher, Set<Tag> tags) {
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setDuration(request.getDuration());
        existing.setStartDate(request.getStartDate());
        existing.setCategory(category);
        existing.setTeacher(teacher);
        existing.setTags(tags);
    }
}


