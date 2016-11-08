package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.Customer;
import com.bbytes.plutus.repo.CustomerRepository;

@Service
public class CustomerService extends AbstractService<Customer, String> {

	protected CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		super(customerRepository);
		this.customerRepository = customerRepository;
	}

	public Customer findByName(String name) {
		return customerRepository.findByName(name);
	}
	
	public Customer findByEmail(String email){
		return customerRepository.findByEmail(email);
	}
}
