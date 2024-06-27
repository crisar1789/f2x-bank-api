package com.f2x.bank.application.service;

import com.f2x.bank.domain.model.Product;

public interface ProductServiceI {

	Product getProductByAccountNumber(String accountNumber);
	Product createProduct(Product product);
	Product updateProduct(String accountNumber, Product product);
    void deleteProduct(Long id);
}
