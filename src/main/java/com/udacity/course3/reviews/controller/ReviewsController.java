package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with {@link Review} entity.
 */
@RestController
public class ReviewsController {
	
	@Autowired
    private ReviewRepository reviewRepository;
	@Autowired
    private ProductRepository productRepository;


    /**
     * Creates a {@link Review} for a {@link Product}.
     *
     * @param productId The id of the product.
     * @param review The {@link Review} to create.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review review) {
        Optional<Product> optional = productRepository.findById(productId);
        if (optional.isPresent()) {
        	// Title has SQL not null constrain
        	if(review.getTitle()!=null){
        		review.setProduct(optional.get());
                return ResponseEntity.ok(reviewRepository.save(review));
        	}else{
        		// in the case Review doesnot contain a title
        		return ResponseEntity
        	            .status(HttpStatus.NOT_ACCEPTABLE)
        	            .body(null);
        	}
            
        } else {
        	//in case product does not exist 
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
    	
    	//check if the product has a reviews or not
       if ((reviewRepository.findAllByProduct(new Product(productId))).isEmpty())	
    		return ResponseEntity.notFound().build();
       
 	   return ResponseEntity.ok(reviewRepository.findAllByProduct(new Product(productId)));
    }
}