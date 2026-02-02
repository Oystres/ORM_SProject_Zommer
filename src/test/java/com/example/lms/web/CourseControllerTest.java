package com.example.lms.web;

import com.example.lms.model.User;
import com.example.lms.model.UserRole;
import com.example.lms.repository.UserRepository;
import com.example.lms.support.PostgresContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest extends PostgresContainerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private Long teacherId;

    @BeforeEach
    void setupTeacher() {
        User t = new User();
        t.setName("Teacher");
        t.setEmail("teacher@example.com");
        t.setRole(UserRole.TEACHER);
        teacherId = userRepository.save(t).getId();
    }

    @Test
    void createAndGetCourse() throws Exception {
        String body = "{\n" +
                "  \"title\": \"Intro Java\",\n" +
                "  \"description\": \"Basics\",\n" +
                "  \"teacherId\": " + teacherId + "\n" +
                "}";

        mockMvc.perform(post("/api/courses").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Intro Java")))
                .andExpect(jsonPath("$.teacherId", is(teacherId.intValue())));

        mockMvc.perform(get("/api/courses"))
                .andExpect(status().isOk());
    }
}


