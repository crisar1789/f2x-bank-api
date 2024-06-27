package com.f2x.bank.interfaces.controller;

import javax.validation.ConstraintViolationException;

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

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.application.service.ProductServiceI;
import com.f2x.bank.domain.model.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return response;
    }
	
	@PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
		} catch(ConstraintViolationException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(),	HttpStatus.BAD_REQUEST);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return response;
    }

    @PutMapping("/{accountNumber}")
    public ResponseEntity<Object> updateProduct(@PathVariable String accountNumber, @RequestBody Product product) {
    	ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(productService.updateProduct(accountNumber, product), HttpStatus.CREATED);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(),	HttpStatus.BAD_REQUEST);
		}
		
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
    	productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
