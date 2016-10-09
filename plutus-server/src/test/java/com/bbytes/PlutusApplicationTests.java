package com.bbytes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
