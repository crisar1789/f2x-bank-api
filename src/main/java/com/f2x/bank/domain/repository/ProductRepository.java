package com.f2x.bank.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.f2x.bank.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
