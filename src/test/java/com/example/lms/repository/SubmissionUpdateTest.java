package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SubmissionUpdateTest extends PostgresContainerTest {

    @Autowired private UserRepository userRepository;
    @Autowired private CourseRepository courseRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private LessonRepository lessonRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private SubmissionRepository submissionRepository;

    @Test
    void updateSubmissionScore() {
        User s = new User();
        s.setName("S");
        s.setEmail("s_sub@example.com");
        s.setRole(UserRole.STUDENT);
        s = userRepository.save(s);

        User t = new User();
        t.setName("T");
        t.setEmail("t_sub@example.com");
        t.setRole(UserRole.TEACHER);
        t = userRepository.save(t);

        Course c = new Course();
        c.setTitle("C");
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

        Assignment a = new Assignment();
        a.setLesson(l);
        a.setTitle("A");
        a = assignmentRepository.save(a);

        Submission sub = new Submission();
        sub.setAssignment(a);
        sub.setStudent(s);
        sub.setContent("answer");
        sub = submissionRepository.save(sub);

        sub.setScore(90);
        sub.setFeedback("good");
        submissionRepository.save(sub);

        Submission reloaded = submissionRepository.findById(sub.getId()).orElseThrow();
        assertThat(reloaded.getScore()).isEqualTo(90);
        assertThat(reloaded.getFeedback()).isEqualTo("good");
    }
}


