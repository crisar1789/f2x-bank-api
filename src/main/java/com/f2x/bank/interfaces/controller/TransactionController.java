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

import com.f2x.bank.application.service.TransactionServiceI;
import com.f2x.bank.domain.model.Transaction;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

	@Autowired
	private TransactionServiceI transactionService;
	
	@GetMapping("/{id}")
    public ResponseEntity<Transaction> getUserById(@PathVariable Long id) {
		Transaction transaction = transactionService.getTransactionById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
	
	@PostMapping
    public ResponseEntity<Transaction> createUser(@RequestBody Transaction transaction) {
		Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }
}
