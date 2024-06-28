package com.f2x.bank.application.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.application.exception.F2XBankException;
import com.f2x.bank.domain.enums.AccountCode;
import com.f2x.bank.domain.enums.StateCode;
import com.f2x.bank.domain.model.Product;
import com.f2x.bank.domain.model.State;
import com.f2x.bank.domain.model.User;
import com.f2x.bank.domain.repository.ProductRepository;
import com.f2x.bank.domain.repository.StateRepository;

@Service
public class ProductServiceImp implements ProductServiceI {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private StateRepository stateRepository;
	
	@Autowired
	private UserServiceI userService;
	
	@Override
	public Product getProductByAccountNumber(String accountNumber) {
		
		return productRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new F2XBankException("Product not found: ", accountNumber));
	}

	@Override
	public Product createProduct(Product product) {
		checkAccountType(product);
		product.setUser(getUser(product.getUser()));
		product.setAccountNumber(generateNewAccountNumber(product.getAccountType().getCode()));
		State state = stateRepository.findByCode(StateCode.A).get();
		product.setState(state);
		product.setCreatedDate(LocalDateTime.now());
        return productRepository.save(product);
	}
	
	private User getUser(User user) {
		if (user == null) {
			throw new F2XBankException("User have to be mandatory");
		}
		return userService.getUserByTypeAndNumberIdentification(user.getIdentificationType().getCode().getCode(), 
				user.getIdentificationNumber());
	}

	@Override
	public Product updateProduct(String accountNumber, Product product) {
		Product existingProduct = getProductByAccountNumber(accountNumber);
		
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
	
	private void checkAccountType(Product product) {
		AccountCode type = AccountCode.valueOf(product.getAccountType().getCode().getCode());
		
		if(type == null) {
			throw new F2XBankException("Invalid account type: ", product.getAccountType().getCode().getCode());
		}
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
