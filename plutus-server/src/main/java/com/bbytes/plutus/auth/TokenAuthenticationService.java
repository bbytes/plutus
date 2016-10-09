package com.bbytes.plutus.auth;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.RequestContextHolder;


public class TokenAuthenticationService {

	private TokenHandler tokenHandler;

	@Autowired
	private AuthUserDBService authUserDBService;

	@Autowired
	private SubscriptionService subscriptionService;

	@PostConstruct
	public void init() {
		tokenHandler = new TokenHandler(authUserDBService);
	}

	public String addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		String token = tokenHandler.createTokenForUser(user, PlutusSecurityConfig.SECRET_KEY);
		response.addHeader(GlobalConstant.AUTH_TOKEN_HEADER, token);
		return token;
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(GlobalConstant.AUTH_TOKEN_HEADER);
		String subscriptionKey = RequestContextHolder.getSubscriptionKey();
		Subscription subscription = subscriptionService.findBySubscriptionKey(subscriptionKey);

		if (token == null || subscription == null) {
			throw new InsufficientAuthenticationException("Auth token missing or subscription key incorrect");
		}

		final User user = tokenHandler.parseUserFromToken(token, subscription.getSubscriptionSecret());
		if (user == null) {
			throw new UsernameNotFoundException("Given user not found in system");
		}

		return new UserAuthentication(user);

	}
}
