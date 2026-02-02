package com.example.lms.service;

import com.example.lms.model.CourseReview;
import com.example.lms.repository.CourseReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CourseReviewService {
    private final CourseReviewRepository courseReviewRepository;

    public CourseReviewService(CourseReviewRepository courseReviewRepository) {
        this.courseReviewRepository = courseReviewRepository;
    }

    public List<CourseReview> findAll() { return courseReviewRepository.findAll(); }

    public CourseReview getById(Long id) { return courseReviewRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Review not found")); }

    public CourseReview create(CourseReview review) { return courseReviewRepository.save(review); }

    public CourseReview update(Long id, CourseReview updated) {
        CourseReview existing = getById(id);
        existing.setCourse(updated.getCourse());
        existing.setStudent(updated.getStudent());
        existing.setRating(updated.getRating());
        existing.setComment(updated.getComment());
        return courseReviewRepository.save(existing);
    }

    public void delete(Long id) { courseReviewRepository.deleteById(id); }
}


