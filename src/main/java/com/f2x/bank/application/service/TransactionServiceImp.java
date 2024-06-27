package com.f2x.bank.application.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.domain.enums.StateCode;
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
                .orElseThrow(() -> new F2XBankException("Transaction not found"));
	}
	
	@Override
	public Transaction createTransaction(Transaction transaction) {
		updateProductBalance(transaction);
		transaction.setTransactionDate(LocalDateTime.now());
        return transactionRepository.save(transaction);
	}
	
	private void updateProductBalance(Transaction transaction) {
		
		BigDecimal value = transaction.getValue();
		Product origin = getProduct(transaction.getAccountOrigin().getAccountNumber());
		checkProducActive(origin);
		transaction.setAccountOrigin(origin);
		
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
			checkAccounts(transaction);
			Product destination = getProduct(transaction.getAccountDestination().getAccountNumber());
			checkProducActive(destination);
			transaction.setAccountDestination(destination);
			transfer(origin, destination, value);
			updateProduct(origin);
			updateProduct(destination);
			break;
		default:
			break;
		}
	}
	
	private void checkProducActive(Product product) {
		if (!product.getState().getCode().getName().equals(StateCode.A.getName())) {
			throw new F2XBankException("Account is not Active: ", product.getAccountNumber());
		}
	}
	
	private void checkAccounts(Transaction transaction ) {
		if (transaction.getAccountDestination().getAccountNumber() == null
				|| transaction.getAccountDestination().getAccountNumber().isEmpty()) {
			throw new F2XBankException("Destination account cannot be the empty");
		}
		
		if (transaction.getAccountOrigin().getAccountNumber()
				.equals(transaction.getAccountDestination().getAccountNumber())) {
			throw new F2XBankException("The source and destination accounts cannot be the same");
		}
	}
	
	private Product getProduct(String accountNumber) {
		return productService.getProductByAccountNumber(accountNumber);
	}
	
	private void withdraw(Product product, BigDecimal value) {
		if (product.getBalance().subtract(value).longValue() < 0) {
			throw new F2XBankException("Insufficient funds to do the movement: ", product.getBalance().toString());
		}
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
		productService.updateProduct(product.getAccountNumber(), product);
	}
}
