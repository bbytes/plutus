package com.bbytes.plutus.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.bbytes.plutus.service.UserService;

@Configuration
@EnableWebSecurity
@Order(2)
public class PlutusSecurityConfig extends WebSecurityConfigurerAdapter {

	public final static String SECRET_KEY = "T3g4hS9XD83r6omVjmA8nHZaa0yATTfM";

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring()
				// All of Spring Security will ignore the requests.
				.antMatchers("/").antMatchers("/signup/**").antMatchers("/connect/**").antMatchers("/status")
				.antMatchers("/{[path:[^\\.]*}").antMatchers("/resources/**").antMatchers("/assets/**").antMatchers("/favicon.ico")
				.antMatchers("/**/*.html").antMatchers("/resources/**").antMatchers("**/register").antMatchers("/static/**")
				.antMatchers("/app/**").antMatchers("/**/*.css").antMatchers("/**/*.js");

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// CSRF Disabled and it is ok. Plz read the explanation from
		// stackoverflow
		/*
		 * Note : "If we go down the cookies way, you really need to do CSRF to
		 * avoid cross site requests. That is something we can forget when using
		 * JWT as you will see." (JWT = Json Web Token, a Token based
		 * authentication for stateless apps)
		 */
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().exceptionHandling().and()
				.servletApi().and().authorizeRequests()

				// Allow logins urls
				// Allow anonymous resource requests
				.antMatchers("/").permitAll().antMatchers("/favicon.ico").permitAll().antMatchers("/**/*.html")
				.permitAll().antMatchers("/**/*.css").permitAll().antMatchers("/**/*.js").permitAll()
				.antMatchers("/auth/**").permitAll().and()
				// Custom Token based authentication based on the header
				// previously given to the client
				.addFilterBefore(new StatelessAuthenticationFilter(tokenAuthenticationService()),
						UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new StatelessLoginFilter("/auth/login", userDetailsService(), userService, tokenAuthenticationService(),
						authenticationManager), StatelessAuthenticationFilter.class)
				.headers().cacheControl();

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(createSimplePasswordAuthenticationProvider()).userDetailsService(userDetailsService())
				.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public AuthenticationProvider createSimplePasswordAuthenticationProvider() {
		return new SimplePasswordAuthenticationProvider();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public AuthUserDBService userDetailsService() {
		return new AuthUserDBService();
	}

	@Bean
	public TokenAuthenticationService tokenAuthenticationService() {
		return new TokenAuthenticationService();
	}

}