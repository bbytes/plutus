package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.User;
import com.bbytes.plutus.repo.UserRepository;

@Service
public class UserService extends AbstractService<User, String> {

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}

}
