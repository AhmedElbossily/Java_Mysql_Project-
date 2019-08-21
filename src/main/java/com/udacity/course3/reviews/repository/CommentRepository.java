package com.udacity.course3.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByReview(Review review);
}