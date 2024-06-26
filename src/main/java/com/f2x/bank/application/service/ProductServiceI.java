package com.f2x.bank.application.service;

import com.f2x.bank.domain.model.Product;

public interface ProductServiceI {

	Product getProductById(Long id);
	Product createProduct(Product product);
	Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
