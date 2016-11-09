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
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.util.GlobalConstant;
import com.bbytes.plutus.util.RequestContextHolder;
import com.bbytes.plutus.util.URLMapping;

import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.lang.Assert;

public class StatelessAuthenticationFilter extends GenericFilterBean {

	private final TokenAuthenticationService tokenAuthenticationService;

	// ignore any register url as it will not have subscription key token or app
	// profile info in header
	protected RequestMatcher ignoreRequestMatcher;

	public StatelessAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService) {
		Assert.notNull(tokenAuthenticationService);
		this.tokenAuthenticationService = tokenAuthenticationService;
		ignoreRequestMatcher = new AntPathRequestMatcher(URLMapping.BASE_API_URL + "/subscription/register", "POST");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (!ignoreRequestMatcher.matches((HttpServletRequest) request)) {
			setRequestContext((HttpServletRequest) request);
			try {
				Authentication authentication = tokenAuthenticationService.getAuthentication((HttpServletRequest) request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (SignatureException ex) {
				throw new AuthenticationServiceException("Auth failed : Signature does not match locally computed signature");
			} catch (Exception e) {
				throw new AuthenticationServiceException("Auth failed");
			}
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
		try {
			AppProfile appProfile = AppProfile.valueOf(appProfileValue);
			RequestContextHolder.setAppProfile(appProfile);
		} catch (Throwable e) {
			// do nothing
		}

		String subscriptionKey = request.getHeader(GlobalConstant.SUBSCRIPTION_KEY_HEADER);
		RequestContextHolder.setSubscriptionKey(subscriptionKey);

	}
}
