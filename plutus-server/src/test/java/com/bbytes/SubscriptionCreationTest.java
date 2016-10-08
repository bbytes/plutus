package com.bbytes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.DeploymentMode;
import com.bbytes.plutus.model.Customer;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.ProductPlan;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.service.ProductPlanService;
import com.bbytes.plutus.service.SubscriptionService;

public class SubscriptionCreationTest extends ProductLicenseWebApplicationTests {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private ProductPlanService productPlanService;

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
		customer.setId(UUID.randomUUID().toString());
		customer.setName("Test customer");
		customer.setWebsite("www.example.com");
		customer.setPrimaryContactNo("+919012345676");
		customer.setPrimaryEmail("test@bbytes.com");

		product = new Product();
		product.setId("STATUSNAP");
		product.setName("Statusnap");
		product.setDesc("Capture status online everyday");
		product.addProductTeamEmails("statusnap@bbytes.com");

		productPlan = new ProductPlan();
		productPlan.setName("Normal Statusnap");
		productPlan.setBillingCycle(BillingCycle.Monthy);
		productPlan.setCurrency(Currency.INR);
		productPlan.setDeploymentMode(DeploymentMode.SAAS);
		productPlan.setDiscount(null);
		productPlan.setId(UUID.randomUUID().toString());
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
		subscription.setSubscriptionKey(UUID.randomUUID().toString());
		subscription.setSubscriptionSecret(UUID.randomUUID().toString());

		subscription.setId(UUID.randomUUID().toString());

		subscription.setBillingAmount(100.5);
		subscription.setValidTill(DateTime.now().plusDays(10).toDate());
		subscription.setEnable(true);
	}

	@Test
	public void testCreate() {

		subscription = subscriptionService.save(subscription);
		
		ProductPlan plan = productPlanService.findOne(subscription.getProductPlan().getId());
		Assert.assertNotNull(plan);

		subscription = subscriptionService.findOne(plan.getSubscription().getId());
		Assert.assertNotNull(subscription);

		ProductPlan plan2 = productPlanService.findOne("dont exist id");
		Assert.assertNull(plan2);
	}

}
