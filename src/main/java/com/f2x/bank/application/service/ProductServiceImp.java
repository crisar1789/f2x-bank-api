package com.f2x.bank.application.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.f2x.bank.domain.model.Product;
import com.f2x.bank.domain.repository.ProductRepository;

@Service
public class ProductServiceImp implements ProductServiceI {

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
	}

	@Override
	public Product createProduct(Product product) {
		product.setCreatedDate(LocalDateTime.now());
		product.setUpdatedDate(LocalDateTime.now());
        return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long id, Product product) {
		Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

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

}
