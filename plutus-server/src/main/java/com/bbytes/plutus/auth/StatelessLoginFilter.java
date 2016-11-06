package com.bbytes.plutus.auth;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter implements AuthenticationFailureHandler {

	private final AuthUserDBService userService;
	private final UserService plutusUserService;;
	private final TokenAuthenticationService tokenAuthenticationService;

	protected StatelessLoginFilter(String urlMapping, AuthUserDBService userService, UserService plutusUserService, TokenAuthenticationService tokenAuthenticationService,
			AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(urlMapping));
		this.userService = userService;
		this.plutusUserService = plutusUserService;
		this.tokenAuthenticationService = tokenAuthenticationService;

		setAuthenticationManager(authManager);
		setAuthenticationFailureHandler(this);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		if (request.getParameter("username") == null) {
			throw new AuthenticationServiceException("Username missing");
		}
		if (request.getParameter("password") == null) {
			throw new AuthenticationServiceException("Password missing");
		}

		final UsernamePasswordAuthenticationToken loginToken = new UsernamePasswordAuthenticationToken(
				request.getParameter("username").toLowerCase().toString(), request.getParameter("password").toString());

		return getAuthenticationManager().authenticate(loginToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authentication) throws IOException, ServletException {

		// Lookup the complete User object from the database and create an
		// Authentication for it
		UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) authentication;

		final User user = userService.loadUserByUsername(authentication.getName());
		
		// Add the custom token as HTTP header to the response
		tokenAuthenticationService.addAuthentication(response, new UserAuthentication(user));

		// Add the authentication to the Security context
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

		/*
		 * Writing user information to response after successful authentication
		 */
		
		final PlutusUser plutusUser = plutusUserService.findOne(user.getUsername());
		plutusUser.setPassword("N/A");
		
		PlutusRestResponse authStatus = new PlutusRestResponse(true, plutusUser);
		ObjectMapper mapper = new ObjectMapper();
		String responseObject = mapper.writeValueAsString(authStatus);
		((HttpServletResponse) response).getWriter().append(responseObject);

	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
		SecurityContextHolder.clearContext();
		String erroMsg = exception.getLocalizedMessage();
		PlutusRestResponse authStatus = new PlutusRestResponse(false, erroMsg);
		ObjectMapper mapper = new ObjectMapper();
		((HttpServletResponse) response).setContentType("application/json");
		((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
		((HttpServletResponse) response).getOutputStream().println(mapper.writeValueAsString(authStatus));

	}

}