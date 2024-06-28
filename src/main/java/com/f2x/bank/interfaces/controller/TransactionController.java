package com.f2x.bank.interfaces.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.application.service.TransactionServiceI;
import com.f2x.bank.domain.model.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionServiceI transactionService;
	
	@GetMapping("/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Long id) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(),	HttpStatus.BAD_REQUEST);
		}
		
        return response;
    }
	
	@PostMapping
    public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction) {
		ResponseEntity<Object> response = null;
		try {
			response = new ResponseEntity<>(transactionService.createTransaction(transaction), HttpStatus.CREATED);
		} catch(F2XBankException e) {
			log.error("Error: {} ", e.getMessage());
			response = new ResponseEntity<>(e.getMessage(),	HttpStatus.BAD_REQUEST);
		}
		
        return response;
    }
}
