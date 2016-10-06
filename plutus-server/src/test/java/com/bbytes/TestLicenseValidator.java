package com.bbytes;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.service.LicenseInvalidException;
import com.bbytes.plutus.service.SubscriptionService;

public class TestLicenseValidator extends ProductLicenseWebApplicationTests {

	@Autowired
	private SubscriptionService validatorService;

	private File licTestFile;
	
	@Before
	public void setup() {
		licTestFile = new File("src/test/resources/testfiles/test.lic");
	}
	
	@Test
	public void testValidateLicenseFile() throws LicenseInvalidException, IOException {
		Subscription licenseData = validatorService.validateLicenseFile(licTestFile);
		Assert.assertTrue(licenseData.getIssuer().equals("BBytes"));
		Assert.assertFalse(licenseData.isExpired());
		System.out.println(licenseData);
	}

	
}