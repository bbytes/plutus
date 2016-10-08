package com.bbytes.plutus.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bbytes.plutus.service.UserService;

public class AuthUserDBService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Autowired
	private UserService userService;

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		com.bbytes.plutus.model.User plutusUser = userService.findOne(username);
		if (plutusUser == null) {
			throw new UsernameNotFoundException("User not found with username :" + username);
		}

		final User user = new AuthUser(plutusUser);
		detailsChecker.check(user);
		return user;
	}

}
