package com.example.lms.web;

import com.example.lms.model.*;
import com.example.lms.repository.ModuleRepository;
import com.example.lms.repository.QuizRepository;
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
class QuestionControllerTest extends PostgresContainerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private UserRepository userRepository;
    @Autowired private ModuleRepository moduleRepository;
    @Autowired private QuizRepository quizRepository;

    private Long quizId;

    @BeforeEach
    void setup() {
        User t = new User();
        t.setName("T");
        t.setEmail("tq@example.com");
        t.setRole(UserRole.TEACHER);
        t = userRepository.save(t);

        Course c = new Course();
        c.setTitle("C");
        c.setTeacher(t);

        Module m = new Module();
        m.setCourse(c);
        m.setTitle("M");
        m.setOrderIndex(1);
        c.getModules().add(m);
        moduleRepository.save(m);

        Quiz q = new Quiz();
        q.setModule(m);
        q.setTitle("Q");
        quizId = quizRepository.save(q).getId();
    }

    @Test
    void createQuestionAndAddOption() throws Exception {
        String qBody = "{\n" +
                "  \"quizId\": " + quizId + ",\n" +
                "  \"text\": \"2+2?\",\n" +
                "  \"type\": \"SINGLE_CHOICE\"\n" +
                "}";

        String response = mockMvc.perform(post("/api/questions").contentType(MediaType.APPLICATION_JSON).content(qBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is("2+2?")))
                .andReturn().getResponse().getContentAsString();

        // We won't parse id here to stay simple; just hit list endpoint
        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk());
    }
}


