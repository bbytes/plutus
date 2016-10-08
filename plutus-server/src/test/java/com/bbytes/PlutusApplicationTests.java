package com.bbytes;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bbytes.plutus.PlutusWebApplication;
import com.bbytes.plutus.service.ProductPlanService;
import com.bbytes.plutus.service.SubscriptionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlutusWebApplication.class)
public class PlutusApplicationTests {

	@Autowired
	protected SubscriptionService subscriptionService;

	@Autowired
	protected ProductPlanService productPlanService;

	@Test
	public void contextLoads() {
	}

}
