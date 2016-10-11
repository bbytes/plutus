package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.repo.ProductRepository;

@Service
public class ProductService extends AbstractService<Product, String> {

	protected ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		super(productRepository);
		this.productRepository = productRepository;
	}

	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

}
