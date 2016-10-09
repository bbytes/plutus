package com.bbytes.plutus.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;
import com.bbytes.plutus.service.SubscriptionCreateException;
import com.bbytes.plutus.service.SubscriptionInvalidException;
import com.bbytes.plutus.service.SubscriptionService;
import com.bbytes.plutus.util.RequestContextHolder;

@RestController
@RequestMapping("v1/api/subscription")
public class SubscriptionRestController {

	@Autowired
	private SubscriptionService subscriptionService;

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

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	private SubscriptionStatusRestResponse create(@RequestBody Subscription subscription)
			throws SubscriptionCreateException {

		if (RequestContextHolder.getAppProfile().isEnterpriseMode()) {
			throw new SubscriptionCreateException("Subscription creation is allowed only for saas mode via api call ");
		}

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