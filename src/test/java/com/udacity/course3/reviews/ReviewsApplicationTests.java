package com.udacity.course3.reviews;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.udacity.course3.reviews.repository.ReviewRepository;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.model.Review;
import com.udacity.course3.reviews.model.Comment;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {
	
	@Autowired private CommentRepository commentRepository;
	@Autowired private ReviewRepository reviewRepository;
	@Autowired private ProductRepository productRepository;	
	
	@Test
	public void injectedComponentsAreNotNull(){
		assertThat(commentRepository).isNotNull();
		assertThat(reviewRepository).isNotNull();
		assertThat(productRepository).isNotNull();
	}
	
	@Test
	public void whenSavedProduct_thenfindProductName() {
		//create product and save it
	    Product product = new Product();
		product.setName("Testing_Product");
		product.setDescription("Testing_Description");
		productRepository.save(product);
		
	    assertThat(productRepository.findById(product.getId())).isNotNull();
	}
	
	@Test
	public void whenSavedProduct_thenfindProductList() {
		Product product = new Product();
		product.setName("Testing_Product");
		product.setDescription("Testing_Description");
		productRepository.save(product);
		
	    assertThat(productRepository.findAll()).isNotEmpty();
	}
	
	@Test
	public void whenSavedReview_ThenfindByProductId() {
		//Create a product
		Product product = new Product();
		product.setName("Testing_Product");
		product.setDescription("Testing_Description");
		productRepository.save(product);
	    //get the product with the given id
		Optional<Product>  optionalFoundProduct = productRepository.findById(product.getId());
		
		//Create Review 
	    Review review = new Review();
		review.setProduct(optionalFoundProduct.get());
		review.setReviewText("Testing_Review");	
		review.setRecommended(true);
		review.setTitle("Test_Title");
		reviewRepository.save(review);		
	
	    assertThat(reviewRepository.findAllByProduct(product)).isNotEmpty();
	}
	
	@Test
	public void whenSavedComment_ThenfindByReviewId() {
		//Create a product
		Product product = new Product();
		product.setName("Testing_Product");
		product.setDescription("Testing_Description");
		productRepository.save(product);
	    //get the product with the given id
		Optional<Product>  optionalFoundProduct = productRepository.findById(product.getId());
		
		//Create Review 
	    Review review = new Review();
		review.setProduct(optionalFoundProduct.get());
		review.setReviewText("Testing_Review");	
		review.setRecommended(true);
		review.setTitle("Test_Title");
		reviewRepository.save(review);		
		Optional<Review>  optionalFoundReview = reviewRepository.findById(review.getId());
		
		// create new comment and assign its values
		Comment comment = new Comment();
		comment.setReview(optionalFoundReview.get());
		comment.setTitle("Title_test");		
		comment.setCommentText("Comment_testing");
		commentRepository.save(comment);
		
	    assertThat(commentRepository.findAllByReview(review)).isNotEmpty();
	}
}