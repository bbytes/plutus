package com.bbytes.plutus.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "X-AUTH-TOKEN";

	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, AuthUserDBService userService) {
		tokenHandler = new TokenHandler(secret, userService);
	}

	public String addAuthentication(HttpServletResponse response, UserAuthentication authentication) {
		final User user = authentication.getDetails();
		String token = tokenHandler.createTokenForUser(user);
		response.addHeader(AUTH_HEADER_NAME, token);
		return token;
	}

	public Authentication getAuthentication(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);

		if (token == null) {
			throw new InsufficientAuthenticationException("Auth token missing");
		}

		final User user = tokenHandler.parseUserFromToken(token);
		if (user == null) {
			throw new UsernameNotFoundException("Given user not found in system");
		}
		
		return new UserAuthentication(user);

	}
}
