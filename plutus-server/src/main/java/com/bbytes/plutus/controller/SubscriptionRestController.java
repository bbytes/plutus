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
import com.bbytes.plutus.repo.SubscriptionRepository;
import com.bbytes.plutus.response.SubscriptionStatus;
import com.bbytes.plutus.service.SubscriptionCreateException;
import com.bbytes.plutus.service.SubscriptionInvalidException;
import com.bbytes.plutus.service.SubscriptionService;

@RestController
@RequestMapping("v1/api/subscription/")
public class SubscriptionRestController {

	@Autowired
	private SubscriptionRepository licenseDataRepository;

	@Autowired
	private SubscriptionService subscriptionService;


	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	SubscriptionStatus validate(@RequestBody String licenseContent) throws SubscriptionInvalidException {
		Subscription licenseData = subscriptionService.validate(licenseContent);
		if (!licenseData.isEnable() || licenseData.isExpired()) {
			throw new SubscriptionInvalidException("license inactive or expired");
		}

		SubscriptionStatus status = new SubscriptionStatus("License check done", true, licenseData.getValidTill().toString(),
				licenseData.getBillingAmount());

		return status;
	}

	@RequestMapping(value = "/validate/{id}", method = RequestMethod.GET)
	SubscriptionStatus validateLicenseId(@PathVariable String id) throws SubscriptionInvalidException {
		Subscription licenseData = licenseDataRepository.findOne(id);
		if (licenseData == null)
			throw new SubscriptionInvalidException("license info missing in central database for given id");

		if (!licenseData.isEnable() || licenseData.isExpired()) {
			throw new SubscriptionInvalidException("license inactive or expired");
		}

		SubscriptionStatus status = new SubscriptionStatus("License check done", true, licenseData.getValidTill().toString(),
				licenseData.getBillingAmount());

		return status;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	String create(@RequestBody Subscription licenseData) throws SubscriptionCreateException {

		try {
			licenseData = licenseDataRepository.save(licenseData);
		} catch (Exception e) {
			throw new SubscriptionCreateException("Failed to save license to storage");
		}

		String licenseContent = subscriptionService.createLicenseContent(licenseData);

		return licenseContent;

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