package com.bbytes.plutus.auth;

import org.springframework.security.core.userdetails.User;

public class AuthUser extends User {

	private static final long serialVersionUID = -1488117438038686212L;

	public AuthUser(com.bbytes.plutus.model.User user) {
		super(user.getEmail(), user.getPassword(), AuthUtil.getAuthority(user.getUserRole()));
	}

}
