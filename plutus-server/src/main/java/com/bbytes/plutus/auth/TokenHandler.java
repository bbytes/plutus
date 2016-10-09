package com.bbytes.plutus.auth;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Assert;

public final class TokenHandler {

	private final AuthUserDBService userService;

	public TokenHandler(AuthUserDBService userService) {
		Assert.notNull(userService);
		this.userService = userService;
	}

	public User parseUserFromToken(String token, String secret) {
		String username = Jwts.parser().setSigningKey(Base64.encode(secret.getBytes())).parseClaimsJws(token).getBody()
				.getSubject();
		return userService.loadUserByUsername(username);
	}

	/**
	 * The tenant id is set only if app profile is saas mode and the tenant id
	 * is set as issuer in jwt claims object
	 * 
	 * @param token
	 * @param secret
	 * @return
	 */
	public String parseTenantIdFromToken(String token, String secret) {
		String tenantId = Jwts.parser().setSigningKey(Base64.encode(secret.getBytes())).parseClaimsJws(token).getBody()
				.getIssuer();
		return tenantId;
	}

	public String createTokenForUser(User user, String secret) {
		Date now = new Date();
		Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
		return Jwts.builder().setId(UUID.randomUUID().toString()).setSubject(user.getUsername()).setIssuedAt(now)
				.setExpiration(expiration).signWith(SignatureAlgorithm.HS512, Base64.encode(secret.getBytes()))
				.compact();
	}
}
