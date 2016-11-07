package com.bbytes.plutus.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbytes.plutus.model.PlutusUser;

@Service
public class AppInitService {

	@Autowired
	private UserService userService;

	@PostConstruct
	protected void init() {
		PlutusUser plutusUser1 = new PlutusUser("test", "test@test.com", "test");
		plutusUser1.setPassword("test");

		PlutusUser plutusUser2 = new PlutusUser("admin", "admin@admin.com", "admin");
		plutusUser2.setPassword("admin");

		userService.create(plutusUser1);
		userService.create(plutusUser2);
	}
}
