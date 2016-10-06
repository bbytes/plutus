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
import com.bbytes.plutus.response.LicenseStatus;
import com.bbytes.plutus.service.LicenseCreateException;
import com.bbytes.plutus.service.LicenseInvalidException;
import com.bbytes.plutus.service.SubscriptionService;

@RestController
@RequestMapping("v1/api/subscription/")
public class SubscriptionRestController {

	@Autowired
	private SubscriptionRepository licenseDataRepository;

	@Autowired
	private SubscriptionService subscriptionService;


	@RequestMapping(value = "/validate", method = RequestMethod.GET)
	LicenseStatus validate(@RequestBody String licenseContent) throws LicenseInvalidException {
		Subscription licenseData = subscriptionService.validate(licenseContent);
		if (!licenseData.isActive() || licenseData.isExpired()) {
			throw new LicenseInvalidException("license inactive or expired");
		}

		LicenseStatus status = new LicenseStatus("License check done", true, licenseData.getValidTill().toString(),
				licenseData.getCount());

		return status;
	}

	@RequestMapping(value = "/validate/{id}", method = RequestMethod.GET)
	LicenseStatus validateLicenseId(@PathVariable String id) throws LicenseInvalidException {
		Subscription licenseData = licenseDataRepository.findOne(id);
		if (licenseData == null)
			throw new LicenseInvalidException("license info missing in central database for given id");

		if (!licenseData.isActive() || licenseData.isExpired()) {
			throw new LicenseInvalidException("license inactive or expired");
		}

		LicenseStatus status = new LicenseStatus("License check done", true, licenseData.getValidTill().toString(),
				licenseData.getCount());

		return status;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	String create(@RequestBody Subscription licenseData) throws LicenseCreateException {

		try {
			licenseData = licenseDataRepository.save(licenseData);
		} catch (Exception e) {
			throw new LicenseCreateException("Failed to save license to storage");
		}

		String licenseContent = subscriptionService.createLicenseContent(licenseData);

		return licenseContent;

	}

	@ExceptionHandler(LicenseCreateException.class)
	public ResponseEntity<LicenseStatus> exceptionCreate(HttpServletRequest req, Exception e) {
		LicenseStatus status = new LicenseStatus(e.getMessage(), false);
		return new ResponseEntity<LicenseStatus>(status, HttpStatus.OK);
	}

	@ExceptionHandler(LicenseInvalidException.class)
	public ResponseEntity<LicenseStatus> exceptionValidate(HttpServletRequest req, Exception e) {
		LicenseStatus status = new LicenseStatus(e.getMessage(), false);
		return new ResponseEntity<LicenseStatus>(status, HttpStatus.OK);
	}
}