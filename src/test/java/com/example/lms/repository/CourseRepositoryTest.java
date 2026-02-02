package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseRepositoryTest extends PostgresContainerTest {

    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;

    @Test
    void saveCourseWithTeacherAndCategory() {
        User teacher = new User();
        teacher.setName("T");
        teacher.setEmail("t@example.com");
        teacher.setRole(UserRole.TEACHER);
        teacher = userRepository.save(teacher);

        Category cat = new Category();
        cat.setName("Programming");
        cat = categoryRepository.save(cat);

        Course course = new Course();
        course.setTitle("Java 101");
        course.setTeacher(teacher);
        course.setCategory(cat);
        course = courseRepository.save(course);

        assertThat(course.getId()).isNotNull();
        assertThat(course.getTeacher().getId()).isEqualTo(teacher.getId());
        assertThat(course.getCategory().getId()).isEqualTo(cat.getId());
    }
}


