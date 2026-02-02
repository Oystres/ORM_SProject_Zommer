package com.example.lms.web;

import com.example.lms.model.Course;
import com.example.lms.model.User;
import com.example.lms.model.UserRole;
import com.example.lms.repository.CourseRepository;
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
class CourseReviewControllerTest extends PostgresContainerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private CourseRepository courseRepository;

    private Long studentId;
    private Long courseId;

    @BeforeEach
    void setupData() {
        User s = new User();
        s.setName("Stu");
        s.setEmail("stu@example.com");
        s.setRole(UserRole.STUDENT);
        studentId = userRepository.save(s).getId();

        User t = new User();
        t.setName("Teach");
        t.setEmail("teach@example.com");
        t.setRole(UserRole.TEACHER);
        t = userRepository.save(t);

        Course c = new Course();
        c.setTitle("Course");
        c.setTeacher(t);
        courseId = courseRepository.save(c).getId();
    }

    @Test
    void createAndListReviews() throws Exception {
        String body = "{\n" +
                "  \"courseId\": " + courseId + ",\n" +
                "  \"studentId\": " + studentId + ",\n" +
                "  \"rating\": 5,\n" +
                "  \"comment\": \"Great!\"\n" +
                "}";

        mockMvc.perform(post("/api/reviews").contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.rating", is(5)))
                .andExpect(jsonPath("$.courseId", is(courseId.intValue())));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk());
    }
}


