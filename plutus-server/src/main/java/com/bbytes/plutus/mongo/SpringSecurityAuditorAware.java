package com.bbytes.plutus.mongo;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		String createdBy = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		
		if (securityContext.getAuthentication() != null)
			createdBy = (String) securityContext.getAuthentication().getPrincipal();
		
		return createdBy == null ? "N/A" : createdBy;
	}
}