package com.bbytes.plutus.auth;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.assertj.core.util.Preconditions;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.codec.Base64;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public final class TokenHandler {

    private final String secret;
    private final AuthUserDBService userService;

    public TokenHandler(String secret, AuthUserDBService userService) {
        this.secret = Base64.encode(secret.getBytes()).toString();
        this.userService = Preconditions.checkNotNull(userService);
    }

    public User parseUserFromToken(String token) {
        String username = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + TimeUnit.HOURS.toMillis(1l));
        return Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}
