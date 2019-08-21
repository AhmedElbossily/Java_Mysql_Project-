package com.udacity.course3.reviews.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.udacity.course3.reviews.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	  
	public Product findProductById(Integer id);
	
}