package com.bbytes.plutus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.auth.PasswordHashService;
import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.repo.UserRepository;

@Service
public class UserService extends AbstractService<PlutusUser, String> {

	@Autowired
	private PasswordHashService passwordHashService;

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		super(userRepository);
		this.userRepository = userRepository;
	}

	/*
	 * Save user
	 */
	public PlutusUser create(PlutusUser user) {
		if (user.getPassword() != null)
			user.setPassword(passwordHashService.encodePassword(user.getPassword()));
		return userRepository.save(user);
	}

	/*
	 * Update user
	 */
	public PlutusUser update(PlutusUser user) {
		return userRepository.save(user);
	}

	/*
	 * Update user password
	 */
	public PlutusUser updatePassword(String email, String newPassword) {
		PlutusUser userFromDb = userRepository.findOne(email);
		if (userFromDb != null) {
			userFromDb.setPassword(passwordHashService.encodePassword(newPassword));
			return userRepository.save(userFromDb);
		}
		return userFromDb;

	}

}
