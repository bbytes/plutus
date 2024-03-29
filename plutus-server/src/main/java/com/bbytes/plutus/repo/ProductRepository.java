package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
  
	public Product findByName(String name);
}
