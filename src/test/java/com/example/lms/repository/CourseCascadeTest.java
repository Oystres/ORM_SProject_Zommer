package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CourseCascadeTest extends PostgresContainerTest {

    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private LessonRepository lessonRepository;

    @Test
    void persistCourseWithModuleAndLesson_cascadeWorks_andDeleteCascades() {
        User t = new User();
        t.setName("T");
        t.setEmail("tcc@example.com");
        t.setRole(UserRole.TEACHER);
        t = userRepository.save(t);

        Course c = new Course();
        c.setTitle("Cascade Test");
        c.setTeacher(t);
        c = courseRepository.save(c);

        Module m = new Module();
        m.setCourse(c);
        m.setTitle("M");
        m.setOrderIndex(1);
        m = moduleRepository.save(m);

        Lesson l = new Lesson();
        l.setModule(m);
        l.setTitle("L");
        l = lessonRepository.save(l);

        Long moduleId = m.getId();
        Long lessonId = l.getId();

        assertThat(courseRepository.findById(c.getId())).isPresent();
        assertThat(moduleRepository.findById(moduleId)).isPresent();
        assertThat(lessonRepository.findById(lessonId)).isPresent();

        courseRepository.deleteById(c.getId());

        assertThat(courseRepository.findById(c.getId())).isNotPresent();
        assertThat(moduleRepository.findById(moduleId)).isNotPresent();
        assertThat(lessonRepository.findById(lessonId)).isNotPresent();
    }
}


