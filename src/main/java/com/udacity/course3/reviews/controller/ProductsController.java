package com.udacity.course3.reviews.controller;


import com.udacity.course3.reviews.model.Product;
import com.udacity.course3.reviews.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Spring REST controller for working with {@link Product} entity.
 */
@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired // This means to get the bean called ProductRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private ProductRepository productRepository;

    /**
     * Creates a {@link Product} entity.
     *
     * @param product The {@link Product} to create.
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody Product product) {
    	//make sure that product name or description are not null
    	//to avoid sql null exception 
    	if(product.getName()!= null &&product.getDescription() != null)
    		productRepository.save(product);
    }

    /**
     * Finds a {@link Product} by id.
     *
     * @param id The id of the product.
     * @return The product if found, or a 404 not found.
     */
    @RequestMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Integer id) {
    	
    	//get the product with the given id
    	Product product = productRepository.findProductById(id);
		 
		// check if the product was found or nor 
		if (product == null)
			//return status 404 
			return new ResponseEntity<>( product, HttpStatus.NOT_FOUND);
				
		//return the product with satus 200
		return new ResponseEntity<>( product, HttpStatus.OK);
    }

    /**
     * Lists all products.
     *
     * @return The list of products.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Product> listProducts() {
        return productRepository.findAll();
    }
}