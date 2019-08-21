package com.udacity.course3.reviews.controller;


import com.udacity.course3.reviews.model.Comment;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with {@link Comment} entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

	@Autowired
    private  ReviewRepository reviewRepository;
	@Autowired
    private  CommentRepository commentRepository;

    /**
     * Creates {@link Comment} for a {@link Review}.
     *
     * @param reviewId The id of the review.
     * @param comment The comment to create.
     * @return the created comment or NOT_FOUND if review is not found.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment comment) {
        Optional<Review> optional = reviewRepository.findById(reviewId);
        if (optional.isPresent()) {
        	// Comment Title has SQL Not null constrain 
        	if(comment.getTitle() != null){
        		comment.setReview(optional.get());
                return ResponseEntity.ok(commentRepository.save(comment));
        	}else{
        		//In the case of missed title as it has SQL NOT Null constrain 
        		return ResponseEntity
        	            .status(HttpStatus.NOT_ACCEPTABLE)
        	            .body(null);       		
        	}
            
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List {@link Comment}s for a {@link Review}.
     *
     * @param reviewId The id of the review.
     * @return The list of comments.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
    	//check if the Review has comments or not
        if ((commentRepository.findAllByReview(new Review(reviewId))).isEmpty())	
     		return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok(commentRepository.findAllByReview(new Review(reviewId)));
    }
}