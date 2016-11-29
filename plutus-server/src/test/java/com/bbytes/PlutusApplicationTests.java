package com.bbytes;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbytes.plutus.PlutusWebApplication;
import com.bbytes.plutus.auth.AuthUserDBService;
import com.bbytes.plutus.auth.AuthUtil;
import com.bbytes.plutus.auth.PlutusSecurityConfig;
import com.bbytes.plutus.auth.TokenHandler;
import com.bbytes.plutus.model.PlutusUser;
import com.bbytes.plutus.service.PaymentHistoryService;
import com.bbytes.plutus.service.PricingPlanService;
import com.bbytes.plutus.service.ProductService;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlutusWebApplication.class)
@ActiveProfiles("test")
public class PlutusApplicationTests {

	@Autowired
	protected SubscriptionService subscriptionService;

	@Autowired
	protected PricingPlanService pricingPlanService;

	@Autowired
	protected ProductService productService;
	
	@Autowired
	protected PaymentHistoryService paymentHistoryService;

	@Autowired
	protected UserService userService;

	protected TokenHandler tokenHandler;

	@Autowired
	private AuthUserDBService authUserDBService;

	@PostConstruct
	public void init() {
		PlutusUser testUser = new PlutusUser("test", "test");
		testUser.setUserRole("TEST");
		testUser.setPassword("test");
		userService.save(testUser);
		tokenHandler = new TokenHandler(authUserDBService);
	}

	protected String getAuthToken(String secret) {
		User user = new User("test", "test", AuthUtil.getAuthority("TEST"));
		return tokenHandler.createTokenForUser(user, secret);
	}
	
	protected String getAuthToken() {
		User user = new User("test", "test", AuthUtil.getAuthority("TEST"));
		return tokenHandler.createTokenForUser(user, PlutusSecurityConfig.SECRET_KEY);
	}
	
	

	@Test
	public void contextLoads() {
	}

}
