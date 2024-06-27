package com.f2x.bank.application.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.domain.enums.AccountCode;
import com.f2x.bank.domain.model.Product;
import com.f2x.bank.domain.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductServiceI {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product getProductByAccountNumber(String accountNumber) {
		
		return productRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Product not found"));
	}

	@Override
	public Product createProduct(Product product) {
		product.setAccountNumber(generateNewAccountNumber(product.getAccountType().getCode()));
		product.setCreatedDate(LocalDateTime.now());
        return productRepository.save(product);
	}

	@Override
	public Product updateProduct(String accountNumber, Product product) {
		Product existingProduct = productRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Product not found"));

		existingProduct.setBalance(product.getBalance());
		existingProduct.setGmfExempt(product.isGmfExempt());
		existingProduct.setState(product.getState());
		existingProduct.setUpdatedDate(LocalDateTime.now());

        return productRepository.save(existingProduct);
	}

	@Override
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}
	
	private String generateNewAccountNumber(AccountCode accountType) {
		
		Random random = new Random();
		StringBuilder randomAccountNumber = null;
		Integer randomNumber = 0;
		Optional<Product> product = null;
		
		do {
			while(randomNumber.toString().length() < 8) {
				randomNumber = random.nextInt(99999999);
			}
			
			randomAccountNumber = new StringBuilder("");
			randomAccountNumber.append(accountType.getNumber()).append(randomNumber);
			product = productRepository.findByAccountNumber(randomAccountNumber.toString());
		} while(product.isPresent());
		
		
		return randomAccountNumber.toString();

	}

}
