package com.example.lms.service;

import com.example.lms.model.Assignment;
import com.example.lms.repository.AssignmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;

    public AssignmentService(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;
    }

    public List<Assignment> findAll() { return assignmentRepository.findAll(); }

    public Assignment getById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Assignment not found"));
    }

    public Assignment create(Assignment assignment) { return assignmentRepository.save(assignment); }

    public Assignment update(Long id, Assignment updated) {
        Assignment existing = getById(id);
        existing.setLesson(updated.getLesson());
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setDueDate(updated.getDueDate());
        existing.setMaxScore(updated.getMaxScore());
        return assignmentRepository.save(existing);
    }

    public void delete(Long id) { assignmentRepository.deleteById(id); }
}


