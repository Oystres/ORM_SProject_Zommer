package com.example.lms.config;

import com.example.lms.model.*;
import com.example.lms.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@Configuration
@Profile("dev")
public class DevDataLoader {

    @Bean
    CommandLineRunner loadDemoData(UserRepository users, CategoryRepository categories, CourseRepository courses,
                                   ModuleRepository modules, LessonRepository lessons, AssignmentRepository assignments,
                                   EnrollmentRepository enrollments, SubmissionRepository submissions,
                                   QuizRepository quizzes, QuestionRepository questions, AnswerOptionRepository options) {
        return args -> {
            User instructor = new User();
            instructor.setName("Teacher One");
            instructor.setEmail("teacher1@example.com");
            instructor.setRole(UserRole.TEACHER);
            instructor = users.save(instructor);

            User learner = new User();
            learner.setName("Student One");
            learner.setEmail("student1@example.com");
            learner.setRole(UserRole.STUDENT);
            learner = users.save(learner);

            Category programmingCategory = new Category();
            programmingCategory.setName("Programming");
            programmingCategory = categories.save(programmingCategory);

            Course springCourse = new Course();
            springCourse.setTitle("Основы Spring");
            springCourse.setDescription("Изучение Spring Framework и Spring Boot для создания современных Java-приложений");
            springCourse.setCategory(programmingCategory);
            springCourse.setTeacher(instructor);
            springCourse.setStartDate(LocalDate.now());
            springCourse = courses.save(springCourse);

            // Модуль 1: Введение в Spring Framework
            com.example.lms.model.Module introModule = new com.example.lms.model.Module();
            introModule.setCourse(springCourse);
            introModule.setTitle("Введение в Spring Framework");
            introModule.setOrderIndex(1);
            introModule = modules.save(introModule);

            Lesson introLesson = new Lesson();
            introLesson.setModule(introModule);
            introLesson.setTitle("Введение в Spring");
            introLesson = lessons.save(introLesson);

            Lesson diLesson = new Lesson();
            diLesson.setModule(introModule);
            diLesson.setTitle("Dependency Injection и IoC");
            diLesson = lessons.save(diLesson);

            Assignment firstAppAssignment = new Assignment();
            firstAppAssignment.setLesson(introLesson);
            firstAppAssignment.setTitle("ДЗ: Настройка первого Spring приложения");
            firstAppAssignment = assignments.save(firstAppAssignment);

            Assignment diPracticeAssignment = new Assignment();
            diPracticeAssignment.setLesson(diLesson);
            diPracticeAssignment.setTitle("ДЗ: Практика с Dependency Injection");
            diPracticeAssignment = assignments.save(diPracticeAssignment);

            // Модуль 2: Spring Boot
            com.example.lms.model.Module bootModule = new com.example.lms.model.Module();
            bootModule.setCourse(springCourse);
            bootModule.setTitle("Spring Boot");
            bootModule.setOrderIndex(2);
            bootModule = modules.save(bootModule);

            Lesson bootBasicsLesson = new Lesson();
            bootBasicsLesson.setModule(bootModule);
            bootBasicsLesson.setTitle("Основы Spring Boot");
            bootBasicsLesson = lessons.save(bootBasicsLesson);

            Lesson autoConfigLesson = new Lesson();
            autoConfigLesson.setModule(bootModule);
            autoConfigLesson.setTitle("Spring Boot Auto-Configuration");
            autoConfigLesson = lessons.save(autoConfigLesson);

            Assignment restApiAssignment = new Assignment();
            restApiAssignment.setLesson(bootBasicsLesson);
            restApiAssignment.setTitle("ДЗ: Создание REST API с Spring Boot");
            restApiAssignment = assignments.save(restApiAssignment);

            // Модуль 3: Spring Data и JPA
            com.example.lms.model.Module dataModule = new com.example.lms.model.Module();
            dataModule.setCourse(springCourse);
            dataModule.setTitle("Spring Data JPA");
            dataModule.setOrderIndex(3);
            dataModule = modules.save(dataModule);

            Lesson jpaLesson = new Lesson();
            jpaLesson.setModule(dataModule);
            jpaLesson.setTitle("Работа с Spring Data JPA");
            jpaLesson = lessons.save(jpaLesson);

            Assignment repositoryAssignment = new Assignment();
            repositoryAssignment.setLesson(jpaLesson);
            repositoryAssignment.setTitle("ДЗ: Создание репозиториев и сущностей");
            repositoryAssignment = assignments.save(repositoryAssignment);

            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(springCourse);
            enrollment.setStudent(learner);
            enrollments.save(enrollment);

            // Тест 1: Введение в Spring
            Quiz springBasicsQuiz = new Quiz();
            springBasicsQuiz.setModule(introModule);
            springBasicsQuiz.setTitle("Тест: Основы Spring Framework");
            springBasicsQuiz = quizzes.save(springBasicsQuiz);

            Question iocQuestion = new Question();
            iocQuestion.setQuiz(springBasicsQuiz);
            iocQuestion.setText("Что означает IoC в Spring?");
            iocQuestion.setType(QuestionType.SINGLE_CHOICE);
            iocQuestion = questions.save(iocQuestion);

            AnswerOption iocCorrectAnswer = new AnswerOption();
            iocCorrectAnswer.setQuestion(iocQuestion);
            iocCorrectAnswer.setText("Inversion of Control");
            iocCorrectAnswer.setCorrect(true);
            options.save(iocCorrectAnswer);

            AnswerOption iocWrongAnswer1 = new AnswerOption();
            iocWrongAnswer1.setQuestion(iocQuestion);
            iocWrongAnswer1.setText("Input of Control");
            iocWrongAnswer1.setCorrect(false);
            options.save(iocWrongAnswer1);

            AnswerOption iocWrongAnswer2 = new AnswerOption();
            iocWrongAnswer2.setQuestion(iocQuestion);
            iocWrongAnswer2.setText("Integration of Components");
            iocWrongAnswer2.setCorrect(false);
            options.save(iocWrongAnswer2);

            Question diQuestion = new Question();
            diQuestion.setQuiz(springBasicsQuiz);
            diQuestion.setText("Что такое Dependency Injection?");
            diQuestion.setType(QuestionType.SINGLE_CHOICE);
            diQuestion = questions.save(diQuestion);

            AnswerOption diCorrectAnswer = new AnswerOption();
            diCorrectAnswer.setQuestion(diQuestion);
            diCorrectAnswer.setText("Паттерн внедрения зависимостей");
            diCorrectAnswer.setCorrect(true);
            options.save(diCorrectAnswer);

            AnswerOption diWrongAnswer = new AnswerOption();
            diWrongAnswer.setQuestion(diQuestion);
            diWrongAnswer.setText("Метод инъекции кода");
            diWrongAnswer.setCorrect(false);
            options.save(diWrongAnswer);

            // Тест 2: Spring Boot
            Quiz bootQuiz = new Quiz();
            bootQuiz.setModule(bootModule);
            bootQuiz.setTitle("Тест: Spring Boot");
            bootQuiz = quizzes.save(bootQuiz);

            Question bootAnnotationQuestion = new Question();
            bootAnnotationQuestion.setQuiz(bootQuiz);
            bootAnnotationQuestion.setText("Какой аннотацией отмечается главный класс Spring Boot приложения?");
            bootAnnotationQuestion.setType(QuestionType.SINGLE_CHOICE);
            bootAnnotationQuestion = questions.save(bootAnnotationQuestion);

            AnswerOption bootAnnotationCorrectAnswer = new AnswerOption();
            bootAnnotationCorrectAnswer.setQuestion(bootAnnotationQuestion);
            bootAnnotationCorrectAnswer.setText("@SpringBootApplication");
            bootAnnotationCorrectAnswer.setCorrect(true);
            options.save(bootAnnotationCorrectAnswer);

            AnswerOption bootAnnotationWrongAnswer = new AnswerOption();
            bootAnnotationWrongAnswer.setQuestion(bootAnnotationQuestion);
            bootAnnotationWrongAnswer.setText("@SpringApplication");
            bootAnnotationWrongAnswer.setCorrect(false);
            options.save(bootAnnotationWrongAnswer);
        };
    }
}


