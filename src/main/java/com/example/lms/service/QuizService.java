package com.example.lms.service;

import com.example.lms.model.Quiz;
import com.example.lms.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<Quiz> findAll() { return quizRepository.findAll(); }

    public Quiz getById(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Quiz not found"));
    }

    public Quiz create(Quiz quiz) { return quizRepository.save(quiz); }

    public Quiz update(Long id, Quiz updated) {
        Quiz existing = getById(id);
        existing.setModule(updated.getModule());
        existing.setTitle(updated.getTitle());
        existing.setTimeLimitMinutes(updated.getTimeLimitMinutes());
        return quizRepository.save(existing);
    }

    public void delete(Long id) { quizRepository.deleteById(id); }
}


