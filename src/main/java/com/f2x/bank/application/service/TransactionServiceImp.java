package com.f2x.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.domain.model.Product;
import com.f2x.bank.domain.model.Transaction;
import com.f2x.bank.domain.repository.TransactionRepository;

@Service
public class TransactionServiceImp implements TransactionServiceI {

	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private ProductServiceI productService;
	
	@Override
	public Transaction getTransactionById(Long id) {
		return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
	}
	
	@Override
	public Transaction createTransaction(Transaction transaction) {
		transaction.setTransactionDate(LocalDateTime.now());
		updateProductBalance(transaction);
        return transactionRepository.save(transaction);
	}
	
	private void updateProductBalance(Transaction transaction) {
		
		BigDecimal value = transaction.getValue();
		Product origin = getProduct(transaction.getAccountOrigin().getId());
		
		switch (transaction.getTransactionType().getCode()) {
		case W:
			withdraw(origin, value);
			updateProduct(origin);
			break;
		case P:
			pay(origin, value);
			updateProduct(origin);
			break;
		case T:
			Product destination = getProduct(transaction.getAccountDestination().getId());
			transfer(origin, destination, value);
			updateProduct(origin);
			updateProduct(destination);
			break;
		default:
			break;
		}
	}
	
	private Product getProduct(Long id) {
		return productService.getProductById(id);
	}
	
	private void withdraw(Product product, BigDecimal value) {
		product.setBalance(product.getBalance().subtract(value));
	}
	
	private void pay(Product product, BigDecimal value) {
		product.setBalance(product.getBalance().add(value));
	}
	
	private void transfer(Product origin, Product destination, BigDecimal value) {
		withdraw(origin, value);
		pay(destination, value);
	}
	
	private void updateProduct(Product product) {
		productService.updateProduct(product.getId(), product);
	}
}
