package com.bbytes.plutus.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.response.SubscriptionStatus;
import com.bbytes.plutus.service.SubscriptionCreateException;
import com.bbytes.plutus.service.SubscriptionInvalidException;
import com.bbytes.plutus.service.SubscriptionService;

@RestController
@RequestMapping("v1/api/subscription")
public class SubscriptionRestController {

	@Autowired
	private SubscriptionService subscriptionService;

	@RequestMapping(value = "/validate/{subscriptionKey}", method = RequestMethod.GET)
	SubscriptionStatus validateLicenseId(@PathVariable String subscriptionKey) throws SubscriptionInvalidException {
		Subscription subscription = subscriptionService.findBysubscriptionKey(subscriptionKey);
		if (subscription == null)
			throw new SubscriptionInvalidException("Subscription Key invalid ");

		if (!subscription.isEnable() || subscription.isExpired() || subscription.isDeactivate()) {
			throw new SubscriptionInvalidException("Subscription inactive or expired or deactivated");
		}

		SubscriptionStatus status = new SubscriptionStatus("Subscription check done", true,
				subscription.getValidTill().toString(), subscription.getBillingAmount(), subscription.getProductPlan().getCurrency());

		return status;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	SubscriptionStatus create(@RequestBody Subscription subscription) throws SubscriptionCreateException {

		try {
			subscription = subscriptionService.save(subscription);
		} catch (Exception e) {
			throw new SubscriptionCreateException("Failed to save subscription info to storage");
		}
		SubscriptionStatus status = new SubscriptionStatus(
				"Subscription created with key " + subscription.getSubscriptionKey(), true);

		return status;

	}

	@ExceptionHandler(SubscriptionCreateException.class)
	public ResponseEntity<SubscriptionStatus> exceptionCreate(HttpServletRequest req, Exception e) {
		SubscriptionStatus status = new SubscriptionStatus(e.getMessage(), false);
		return new ResponseEntity<SubscriptionStatus>(status, HttpStatus.OK);
	}

	@ExceptionHandler(SubscriptionInvalidException.class)
	public ResponseEntity<SubscriptionStatus> exceptionValidate(HttpServletRequest req, Exception e) {
		SubscriptionStatus status = new SubscriptionStatus(e.getMessage(), false);
		return new ResponseEntity<SubscriptionStatus>(status, HttpStatus.OK);
	}
}