package com.f2x.bank.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f2x.bank.application.service.ProductServiceI;
import com.f2x.bank.domain.model.Product;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductServiceI productService;
	
	@GetMapping("/{accountNumber}")
    public ResponseEntity<Object> getProductByAccountNumber(@PathVariable String accountNumber) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(productService.getProductByAccountNumber(accountNumber), HttpStatus.OK);
		} catch(RuntimeException e) {
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return response;
    }
	
	@PostMapping
    public ResponseEntity<Product> createUser(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<Product> updateUser(@PathVariable String accountNumber, @RequestBody Product product) {
    	Product updatedProduct = productService.updateProduct(accountNumber, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    	productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
