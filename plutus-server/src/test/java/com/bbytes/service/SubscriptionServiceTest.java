package com.bbytes.service;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bbytes.PlutusApplicationTests;
import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.model.Customer;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.ProductPlan;
import com.bbytes.plutus.model.Subscription;

public class SubscriptionServiceTest extends PlutusApplicationTests {

	private Subscription subscription;

	private Customer customer;

	private ProductPlan productPlan;

	private Product product;

	@Before
	public void setup() {
		customer = new Customer();
		customer.setBillingAddress("Test address");
		customer.addBillingEmails("test@bbytes.com");
		customer.setCreationDate(DateTime.now().toDate());
		customer.setId("Test customer");
		customer.setName("Test customer");
		customer.setWebsite("www.example.com");
		customer.setContactNo("+919012345676");
		customer.setEmail("test@bbytes.com");

		product = new Product();
		product.setId("STATUSNAP");
		product.setName("Statusnap");
		product.setDesc("Capture status online everyday");
		product.addProductTeamEmails("statusnap@bbytes.com");

		productPlan = new ProductPlan();
		productPlan.setName("TEST");
		productPlan.setBillingCycle(BillingCycle.Monthy);
		productPlan.setCurrency(Currency.INR);
		productPlan.setAppProfile(AppProfile.saas);
		productPlan.setDiscount(null);
		productPlan.setId("TEST");
		productPlan.setProduct(product);

		Map<String, Number> productPlanItemToCost = new HashMap<>();
		productPlanItemToCost.put("User", 120);
		productPlanItemToCost.put("Project", 100);
		productPlan.setProductPlanItemToCost(productPlanItemToCost);

		subscription = new Subscription();
		subscription.setBillingAmount(0);
		subscription.setCustomer(customer);
		subscription.setDeactivate(false);
		subscription.setDeactivateReason("none");
		subscription.setEnable(true);
		subscription.setProductPlan(productPlan);
		subscription.setSubscriptionKey("TEST");
		subscription.setSubscriptionSecret("TEST");
		subscription.setId("TEST");
		subscription.setBillingAmount(100.5);
		subscription.setValidTill(DateTime.now().plusDays(10).toDate());
		subscription.setEnable(true);
		subscription.setTrialPeriodInterval(new Interval(DateTime.now(),DateTime.now().plusDays(15)));
		subscription.setSupportPeriodInterval(new Interval(DateTime.now(),DateTime.now().plusDays(200)));
	}

	@Test
	public void testCreate() {

		subscription = subscriptionService.save(subscription);
		
		ProductPlan plan = productPlanService.findOne(subscription.getProductPlan().getId());
		Assert.assertNotNull(plan);

		subscription = subscriptionService.findOne(plan.getSubscription().getId());
		Assert.assertNotNull(subscription);
		Assert.assertNotNull(subscription.getSupportPeriodInterval());
		System.out.println(subscription.getTrialPeriodInterval().getEndMillis());

		ProductPlan plan2 = productPlanService.findOne("dont exist id");
		Assert.assertNull(plan2);
	}

}
