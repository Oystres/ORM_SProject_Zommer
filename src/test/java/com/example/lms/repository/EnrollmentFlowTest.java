package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EnrollmentFlowTest extends PostgresContainerTest {

    @Autowired private EnrollmentRepository enrollmentRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;

    @Test
    void enrollAndQueryByStudentAndCourse() {
        User s = new User();
        s.setName("S");
        s.setEmail("s_en@example.com");
        s.setRole(UserRole.STUDENT);
        s = userRepository.save(s);

        User t = new User();
        t.setName("T");
        t.setEmail("t_en@example.com");
        t.setRole(UserRole.TEACHER);
        t = userRepository.save(t);

        Course c = new Course();
        c.setTitle("C");
        c.setTeacher(t);
        c = courseRepository.save(c);

        Enrollment e = new Enrollment();
        e.setStudent(s);
        e.setCourse(c);
        enrollmentRepository.save(e);

        List<Enrollment> byStudent = enrollmentRepository.findByStudent(s);
        List<Enrollment> byCourse = enrollmentRepository.findByCourse(c);

        assertThat(byStudent).hasSize(1);
        assertThat(byCourse).hasSize(1);
        assertThat(byStudent.get(0).getCourse().getId()).isEqualTo(c.getId());
    }
}


