package com.bbytes;

import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbytes.plutus.enums.SubscriptionType;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.service.SubscriptionService;

public class SubscriptionCreationTest extends ProductLicenseWebApplicationTests {

	@Autowired
	private SubscriptionService subscriptionService;

	private Subscription subscription;

	@Before
	public void setup() {
		subscription = new Subscription();
		subscription.setId(UUID.randomUUID().toString());
		subscription.setType(SubscriptionType.SAAS);
		subscription.setBillingAmount(100.5);
		subscription.setValidTill(DateTime.now().plusDays(10).toDate());
		subscription.setEnable(true);
	}



	
}
