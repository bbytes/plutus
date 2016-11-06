package com.bbytes.plutus.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class SimplePasswordAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordHashService passwordHashService;

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {

		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) auth;

		String username = (auth.getPrincipal() == null) ? null : auth.getPrincipal().toString();
		String password = (auth.getCredentials() == null) ? null : auth.getCredentials().toString();
		
		if(username==null)
			throw new UsernameNotFoundException("Login request missing username");
		
		if(password==null)
			throw new BadCredentialsException("Login request missing password");

		UserDetails userFromDB = userDetailsService.loadUserByUsername(username);
		
		if(userFromDB==null)
			throw new UsernameNotFoundException("User not found with email '" + username+"'");
		
		if (!passwordHashService.passwordMatches(password, userFromDB.getPassword())) {
			throw new BadCredentialsException("Login Failed. Bad credentials");
		}

		return token;

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}


}