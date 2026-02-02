package com.example.lms.repository;

import com.example.lms.model.*;
import com.example.lms.support.PostgresContainerTest;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class LazyLoadingTest extends PostgresContainerTest {

    @Autowired private CourseRepository courseRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private TransactionTemplate tx;

    @Test
    void accessingLazyCollectionOutsideTx_throwsLazyInitializationException() {
        Long courseId = tx.execute(status -> {
            User t = new User();
            t.setName("T");
            t.setEmail("t_lazy@example.com");
            t.setRole(UserRole.TEACHER);
            t = userRepository.save(t);

            Course c = new Course();
            c.setTitle("Lazy Course");
            c.setTeacher(t);
            c = courseRepository.save(c);

            Module m = new Module();
            m.setCourse(c);
            m.setTitle("M");
            m.setOrderIndex(1);
            moduleRepository.save(m);
            return c.getId();
        });

        Course detached = courseRepository.findById(courseId).orElseThrow();
        assertThatThrownBy(() -> detached.getModules().size())
                .isInstanceOf(LazyInitializationException.class);
    }
}


