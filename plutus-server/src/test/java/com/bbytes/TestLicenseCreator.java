package com.bbytes;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbytes.plutus.model.Product;
import com.bbytes.plutus.model.Subscription;
import com.bbytes.plutus.model.SubscriptionType;
import com.bbytes.plutus.service.LicenseCreateException;
import com.bbytes.plutus.service.SubscriptionService;

public class TestLicenseCreator extends ProductLicenseWebApplicationTests {

	@Autowired
	private SubscriptionService licGeneratorService;

	private Subscription licenseData;

	@Before
	public void setup() {
		licenseData = new Subscription();
		licenseData.setId(UUID.randomUUID().toString());
		licenseData.setIssuer("BBytes");
		licenseData.setOwner("google-tech");
		licenseData.setProduct(new Product());
		licenseData.setType(SubscriptionType.Trial_License);
		licenseData.setCount(5);
		licenseData.setValidTill(DateTime.now().plusDays(10).toDate());
		licenseData.setActive(true);
	}

	@Test
	public void testCreate() throws LicenseCreateException {
		String licContent = licGeneratorService.createLicenseContent(licenseData);
		System.out.println("Lic Content : " + licContent);
		Assert.assertFalse(licContent.isEmpty());
	}

	@Test
	public void testCreateLicFile() throws IOException, LicenseCreateException {

		File licFile= licGeneratorService.createLicenseFile(licenseData);
		System.out.println("Lic File location  : " + licFile.getPath());
		Assert.assertTrue(licFile.exists());
	}
}
