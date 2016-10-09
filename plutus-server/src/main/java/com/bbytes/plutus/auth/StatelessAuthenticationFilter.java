package com.bbytes.plutus.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.RequestContextHolder;

import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.lang.Assert;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;

	public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		Assert.notNull(tokenAuthenticationService);
		this.tokenAuthenticationService = tokenAuthenticationService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		setRequestContext((HttpServletRequest) request);
		try {
			Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (SignatureException ex) {
			throw new AuthenticationServiceException(
					"Auth failed : Signature does not match locally computed signature");
		} catch (Exception e) {
			throw new AuthenticationServiceException("Auth failed");
		}

		filterChain.doFilter(request, response);
		SecurityContextHolder.getContext().setAuthentication(null);
	}

	/**
	 * Set the request content which will be used with in the app to check like
	 * if the request is from saas server or enterprise product server
	 * 
	 * @param request
	 */
	private void setRequestContext(HttpServletRequest request) {
		String appProfileValue = request.getHeader(GlobalConstant.APP_PROFILE_HEADER);
		if (appProfileValue == null) {
			throw new InsufficientAuthenticationException("App profile information missing in header");
		}

		try {
			AppProfile appProfile = AppProfile.valueOf(appProfileValue);
			RequestContextHolder.setAppProfile(appProfile);
		} catch (Throwable e) {
			throw new InsufficientAuthenticationException("App profile value incorrect");
		}

		String subscriptionKey = request.getHeader(GlobalConstant.SUBSCRIPTION_KEY_HEADER);
		if (subscriptionKey == null) {
			throw new InsufficientAuthenticationException("Subscription key missing in header");
		}

		RequestContextHolder.setSubscriptionKey(subscriptionKey);

	}
}
