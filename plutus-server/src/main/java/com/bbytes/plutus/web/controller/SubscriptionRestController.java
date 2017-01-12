package com.bbytes.plutus.web.controller;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.BillingInfo;
import com.bbytes.plutus.model.Customer;
import com.bbytes.plutus.model.PricingPlan;
import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.response.PlutusRestResponse;
import com.bbytes.plutus.response.SubscriptionRegisterRestResponse;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;
import com.bbytes.plutus.service.CustomerService;
import com.bbytes.plutus.service.PlutusException;
import com.bbytes.plutus.service.PricingPlanService;
import com.bbytes.plutus.service.ProductService;
import com.bbytes.plutus.service.SubscriptionCreateException;
import com.bbytes.plutus.service.SubscriptionInvalidException;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.util.KeyUtil;
import com.bbytes.plutus.util.RequestContextHolder;
import com.bbytes.plutus.util.URLMapping;

@RestController
@RequestMapping(URLMapping.SUBSCRIPTION_URL)
public class SubscriptionRestController {

	@Autowired
	private SubscriptionService subscriptionService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PricingPlanService pricingPlanService;

	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	private PlutusRestResponse getAll() throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, subscriptionService.findAll());
		return status;
	}

	@RequestMapping(value = "/{productName}", method = RequestMethod.GET)
	private PlutusRestResponse getByProductName(@PathVariable String productName) throws PlutusException {
		PlutusRestResponse status = new PlutusRestResponse(true, subscriptionService.findByProductName(productName));
		return status;
	}

	@RequestMapping(value = "/status/{subscriptionKey}", method = RequestMethod.POST)
	private PlutusRestResponse activateDeactivate(@PathVariable String subscriptionKey) throws SubscriptionInvalidException {
		Subscription subscription = subscriptionService.findBySubscriptionKey(subscriptionKey);
		if (subscription == null)
			throw new SubscriptionInvalidException("Subscription Key invalid ");

		subscription.setDeactivate(!subscription.isDeactivate());
		subscription = subscriptionService.save(subscription);
		PlutusRestResponse status = new PlutusRestResponse(true, subscription);
		return status;
	}

	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	private SubscriptionStatusRestResponse validateSubscription() throws SubscriptionInvalidException {
		Subscription subscription = subscriptionService.findBySubscriptionKey(RequestContextHolder.getSubscriptionKey());
		if (subscription == null)
			throw new SubscriptionInvalidException("Subscription Key invalid ");

		if (!subscription.isEnable() || subscription.isExpired() || subscription.isDeactivate()) {
			throw new SubscriptionInvalidException("Subscription inactive or expired or deactivated");
		}

		SubscriptionStatusRestResponse status = new SubscriptionStatusRestResponse("Subscription check done", true,
				subscription.getValidTill().toString(), subscription.getBillingAmount(), subscription.getPricingPlan().getCurrency(),
				subscription.getPricingPlan());

		return status;
	}

	@RequestMapping(value = "/feature/{id}", method = RequestMethod.POST)
	private PlutusRestResponse addFeatureData(@PathVariable String id, @RequestBody Map<String, Object> featureData)
			throws PlutusException {
		if (id == null)
			throw new PlutusException("Subscription id is empty or null");

		PlutusRestResponse response = null;

		Subscription subscription = subscriptionService.findOne(id);
		if (subscription != null) {
			subscription.setProductFeatureToValueMap(featureData);
			subscriptionService.save(subscription);
			response = new PlutusRestResponse(true, subscription);
		} else {
			response = new PlutusRestResponse("Subscription for given id missing", false);
		}

		return response;
	}

	@RequestMapping(value = "/feature/{id}", method = RequestMethod.GET)
	private PlutusRestResponse getFeatureDataMap(@PathVariable String id) throws PlutusException {
		if (id == null)
			throw new PlutusException("Subscription id is empty or null");

		PlutusRestResponse response = null;

		Subscription subscription = subscriptionService.findOne(id);
		if (subscription != null) {
			response = new PlutusRestResponse(true, subscription.getProductFeatureToValueMap());
		} else {
			response = new PlutusRestResponse("Subscription for given id missing", false);
		}

		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	private PlutusRestResponse delete(@PathVariable String id) throws PlutusException {
		if (id == null)
			throw new PlutusException("Subscription id is empty or null");

		subscriptionService.delete(id);

		PlutusRestResponse status = new PlutusRestResponse("Subscription deleted", true);
		return status;
	}

	@RequestMapping(value = "/paymentHistory/{subscriptionKey}", method = RequestMethod.GET)
	private PlutusRestResponse getPaymentHistory(@PathVariable String subscriptionKey) throws SubscriptionInvalidException {
		Subscription subscription = subscriptionService.findBySubscriptionKey(subscriptionKey);
		if (subscription == null)
			throw new SubscriptionInvalidException("Subscription Key invalid ");

		if (!subscription.isEnable() || subscription.isExpired() || subscription.isDeactivate()) {
			throw new SubscriptionInvalidException("Subscription inactive or expired or deactivated");
		}

		PlutusRestResponse status = new PlutusRestResponse(true, subscription.getPaymentHistoryList());
		return status;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	private SubscriptionRegisterRestResponse create(@RequestBody SubscriptionInfo subscriptionInfo) throws SubscriptionCreateException {
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
		subscription.setProductName(subscriptionInfo.getProductName());
		subscription.setTenantId(subscriptionInfo.getTenantId());
		subscription.setValidTill(DateTime.now().plusYears(10).toDate());

		List<PricingPlan> pricingPlanList = pricingPlanService.findByProduct(product);
		if (pricingPlanList != null && !pricingPlanList.isEmpty())
			subscription.setPricingPlan(pricingPlanList.get(0));

		subscription.setSubscriptionKey(KeyUtil.getSubscriptionKey());
		subscription.setSubscriptionSecret(KeyUtil.getSubscriptionSecret());

		try {
			subscription = subscriptionService.save(subscription);
		} catch (Exception e) {
			throw new SubscriptionCreateException("Failed to save subscription info to storage");
		}
		SubscriptionRegisterRestResponse status = new SubscriptionRegisterRestResponse(
				"Subscription created with key " + subscription.getSubscriptionKey(), true, subscription.getSubscriptionKey(),
				subscription.getSubscriptionSecret());

		return status;

	}

	@RequestMapping(value = "/billingInfo", method = RequestMethod.POST)
	private PlutusRestResponse updateBillingInfo(@RequestBody BillingInfo billingInfo) throws SubscriptionCreateException {
		Customer customer = customerService.findByEmail(billingInfo.getEmail());
		if (customer == null)
			throw new SubscriptionCreateException("Customer with email '" + billingInfo.getEmail() + "'  not found");

		// customer.setId(billingInfo.getEmail());
		customer.setName(billingInfo.getName());
		customer.setBillingAddress(billingInfo.getBillingAddress());
		customer.setContactNo(billingInfo.getContactNo());
		// customer.setEmail(billingInfo.getEmail());

		customerService.save(customer);

		PlutusRestResponse status = new PlutusRestResponse("Billing information saved successfully,", true);
		return status;

	}

	@ExceptionHandler(PlutusException.class)
	public ResponseEntity<PlutusRestResponse> exception(HttpServletRequest req, PlutusException e) {
		PlutusRestResponse status = new PlutusRestResponse(e.getMessage(), false);
		return new ResponseEntity<PlutusRestResponse>(status, HttpStatus.OK);
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