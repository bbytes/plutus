package com.bbytes.plutus.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class AuthUtil {

	public static List<SimpleGrantedAuthority> getAuthority(String userRole) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(userRole));
		return authorities;
	}

}
