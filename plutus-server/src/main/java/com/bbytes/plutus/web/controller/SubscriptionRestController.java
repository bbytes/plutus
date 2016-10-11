package com.bbytes.plutus.web.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.Customer;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.ProductPlan;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;
import com.bbytes.plutus.service.BillingService;
import com.bbytes.plutus.service.CustomerService;
import com.bbytes.plutus.service.ProductService;
import com.bbytes.plutus.service.SubscriptionCreateException;
import com.bbytes.plutus.service.SubscriptionInvalidException;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.util.KeyUtil;
import com.bbytes.plutus.util.RequestContextHolder;

@RestController
@RequestMapping("v1/api/subscription")
public class SubscriptionRestController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ProductService productService;

	@Autowired
	private BillingService billingService;

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	private SubscriptionStatusRestResponse validateSubscription() throws SubscriptionInvalidException {
		Subscription subscription = subscriptionService
				.findBySubscriptionKey(RequestContextHolder.getSubscriptionKey());
		if (subscription == null)
			throw new SubscriptionInvalidException("Subscription Key invalid ");

		if (!subscription.isEnable() || subscription.isExpired() || subscription.isDeactivate()) {
			throw new SubscriptionInvalidException("Subscription inactive or expired or deactivated");
		}

		SubscriptionStatusRestResponse status = new SubscriptionStatusRestResponse("Subscription check done", true,
				subscription.getValidTill().toString(), subscription.getBillingAmount(),
				subscription.getProductPlan().getCurrency());

		return status;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	private SubscriptionStatusRestResponse create(@RequestBody SubscriptionInfo subscriptionInfo)
			throws SubscriptionCreateException {

		Product product = productService.findByName(subscriptionInfo.getProductName());
		if (product == null)
			throw new SubscriptionCreateException("Subscription registration failed , product name not available");

		Customer customer = customerService.findOne(subscriptionInfo.getCustomerName());
		if (customer == null) {
			customer = new Customer();
			customer.setId(subscriptionInfo.getCustomerName());
			customer.setName(subscriptionInfo.getCustomerName());
			customer.setBillingAddress(subscriptionInfo.getBillingAddress());
			customer.setContactNo(subscriptionInfo.getContactNo());
			customer.setEmail(subscriptionInfo.getEmail());
			customerService.save(customer);
		}

		Subscription subscription = new Subscription();
		subscription.setCustomer(customer);
		subscription.setId(UUID.randomUUID().toString());
		subscription.setName(subscriptionInfo.getProductName() + ":" + customer.getName());

		ProductPlan productPlan = new ProductPlan();
		productPlan.setId(UUID.randomUUID().toString());
		productPlan.setAppProfile(subscriptionInfo.getAppProfile());
		productPlan.setBillingCycle(subscriptionInfo.getBillingCycle());
		productPlan.setCurrency(subscriptionInfo.getCurrency());
		productPlan.setName(subscriptionInfo.getProductName() + ":" + customer.getName());
		productPlan.setProduct(product);
		productPlan.setProductPlanItemToCost(billingService.getProductCostMap(product.getName()));
		subscription.setProductPlan(productPlan);
		subscription.setSubscriptionKey(KeyUtil.getSubscriptionKey());
		subscription.setSubscriptionSecret(KeyUtil.getSubscriptionSecret());

		try {
			subscription = subscriptionService.save(subscription);
		} catch (Exception e) {
			throw new SubscriptionCreateException("Failed to save subscription info to storage");
		}
		SubscriptionStatusRestResponse status = new SubscriptionStatusRestResponse(
				"Subscription created with key " + subscription.getSubscriptionKey(), true);

		return status;

	}

	@ExceptionHandler(SubscriptionCreateException.class)
	public ResponseEntity<SubscriptionStatusRestResponse> exceptionCreate(HttpServletRequest req, Exception e) {
		SubscriptionStatusRestResponse status = new SubscriptionStatusRestResponse(e.getMessage(), false);
		return new ResponseEntity<SubscriptionStatusRestResponse>(status, HttpStatus.OK);
	}

	@ExceptionHandler(SubscriptionInvalidException.class)
	public ResponseEntity<SubscriptionStatusRestResponse> exceptionValidate(HttpServletRequest req, Exception e) {
		SubscriptionStatusRestResponse status = new SubscriptionStatusRestResponse(e.getMessage(), false);
		return new ResponseEntity<SubscriptionStatusRestResponse>(status, HttpStatus.OK);
	}
}