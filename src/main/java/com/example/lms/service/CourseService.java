package com.example.lms.service;

import com.example.lms.model.Course;
import com.example.lms.model.Lesson;
import com.example.lms.model.Module;
import com.example.lms.model.User;
import com.example.lms.repository.CourseRepository;
import com.example.lms.repository.LessonRepository;
import com.example.lms.repository.ModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    public CourseService(CourseRepository courseRepository, ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<Course> findAll() { return courseRepository.findAll(); }

    public Course getById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Course not found"));
    }

    public List<Course> findByTeacher(User teacher) { return courseRepository.findByTeacher(teacher); }

    public Course create(Course course) { return courseRepository.save(course); }

    public Course update(Long id, Course updated) {
        Course existing = getById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDuration(updated.getDuration());
        existing.setStartDate(updated.getStartDate());
        existing.setCategory(updated.getCategory());
        existing.setTeacher(updated.getTeacher());
        existing.setTags(updated.getTags());
        return courseRepository.save(existing);
    }

    public Module addModule(Long courseId, Module module) {
        Course course = getById(courseId);
        module.setCourse(course);
        return moduleRepository.save(module);
    }

    public Lesson addLesson(Long moduleId, Lesson lesson) {
        Module module = moduleRepository.findById(moduleId).orElseThrow(() -> new NoSuchElementException("Module not found"));
        lesson.setModule(module);
        return lessonRepository.save(lesson);
    }

    public void delete(Long id) { courseRepository.deleteById(id); }
}


