package com.bbytes.plutus.mongo;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return (String) securityContext.getAuthentication().getPrincipal();
	}
}