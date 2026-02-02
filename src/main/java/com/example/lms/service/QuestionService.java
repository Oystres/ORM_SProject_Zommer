package com.example.lms.service;

import com.example.lms.model.AnswerOption;
import com.example.lms.model.Question;
import com.example.lms.repository.AnswerOptionRepository;
import com.example.lms.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerOptionRepository answerOptionRepository;

    public QuestionService(QuestionRepository questionRepository, AnswerOptionRepository answerOptionRepository) {
        this.questionRepository = questionRepository;
        this.answerOptionRepository = answerOptionRepository;
    }

    public List<Question> findAll() { return questionRepository.findAll(); }

    public Question getById(Long id) { return questionRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Question not found")); }

    public Question create(Question q) { return questionRepository.save(q); }

    public Question update(Long id, Question updated) {
        Question existing = getById(id);
        existing.setQuiz(updated.getQuiz());
        existing.setText(updated.getText());
        existing.setType(updated.getType());
        return questionRepository.save(existing);
    }

    public void delete(Long id) { questionRepository.deleteById(id); }

    public AnswerOption addOption(AnswerOption option) { return answerOptionRepository.save(option); }

    public void deleteOption(Long optionId) { answerOptionRepository.deleteById(optionId); }
}


