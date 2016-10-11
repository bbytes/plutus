package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
 
	public Customer findByName(String name);
}
