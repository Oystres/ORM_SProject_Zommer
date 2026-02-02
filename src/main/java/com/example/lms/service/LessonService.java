package com.example.lms.service;

import com.example.lms.model.Lesson;
import com.example.lms.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;

    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    public List<Lesson> findAll() { return lessonRepository.findAll(); }

    public Lesson getById(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Lesson not found"));
    }

    public Lesson create(Lesson lesson) { return lessonRepository.save(lesson); }

    public Lesson update(Long id, Lesson updated) {
        Lesson existing = getById(id);
        existing.setModule(updated.getModule());
        existing.setTitle(updated.getTitle());
        existing.setContent(updated.getContent());
        existing.setVideoUrl(updated.getVideoUrl());
        return lessonRepository.save(existing);
    }

    public void delete(Long id) { lessonRepository.deleteById(id); }
}


