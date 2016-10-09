
package com.bbytes.plutus.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;

import com.bbytes.plutus.enums.AppProfile;

public class AbstractPlutusClientTest {

	protected static final String BASE_URL = "http://localhost:9000/v1/api";

	protected static final String subscriptionKey = "TEST";

	protected static final String subscriptionSecret = "TEST";

	protected static final String tenantId = "TEST";

	protected PlutusClient plutusClient;

	@Before
	public void setup() throws FileNotFoundException, IOException {
		plutusClient = PlutusClient.createSaas(BASE_URL, subscriptionKey, subscriptionSecret, tenantId,
				AppProfile.saas);
	}

}