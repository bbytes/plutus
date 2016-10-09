package com.bbytes.plutus.auth;

import org.springframework.security.core.userdetails.User;

import com.bbytes.plutus.model.Customer;

public class AuthUser extends User {

	private static final long serialVersionUID = -1488117438038686212L;

	public AuthUser(com.bbytes.plutus.model.PlutusUser user) {
		super(user.getEmail(), user.getPassword(), AuthUtil.getAuthority(user.getUserRole()));
	}

	public AuthUser(Customer customer) {
		super(customer.getEmail(), "N/A", AuthUtil.getAuthority("EXTERNAL"));
	}

}
