package com.bbytes.plutus.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.service.UserService;

public class AuthUserDBService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private SubscriptionService subscriptionService;

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		PlutusUser plutusUser = userService.findOne(username);

		if (plutusUser != null) {
			final User user = new AuthUser(plutusUser);
			return user;
		}

		// external request the user name is subscriptionKey
		if (plutusUser == null) {
			Subscription subscription = subscriptionService.findBysubscriptionKey(username);
			if (subscription != null && subscription.getCustomer() != null) {
				final User user = new AuthUser(subscription.getCustomer());
				return user;
			}
		}

		throw new UsernameNotFoundException("User not found with username or subscription key :" + username);

	}

}
