package com.example.lms.repository;

import com.example.lms.model.User;
import com.example.lms.model.UserRole;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest extends PostgresContainerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindByEmail() {
        User u = new User();
        u.setName("Alice");
        u.setEmail("alice@example.com");
        u.setRole(UserRole.STUDENT);
        userRepository.save(u);

        Optional<User> found = userRepository.findByEmail("alice@example.com");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Alice");
        assertThat(found.get().getRole()).isEqualTo(UserRole.STUDENT);
    }
}


