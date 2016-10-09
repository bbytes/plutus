package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.repo.UserRepository;

@Service
public class UserService extends AbstractService<PlutusUser, String> {

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
	}

}
