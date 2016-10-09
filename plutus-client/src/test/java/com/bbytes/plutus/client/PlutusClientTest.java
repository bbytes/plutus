
package com.bbytes.plutus.client;

import org.junit.Assert;
import org.junit.Test;

import com.bbytes.plutus.response.SubscriptionStatusResponse;

public class PlutusClientTest extends AbstractPlutusClientTest {

	@Test
	public void testValidate() throws  PlutusClientException {
		SubscriptionStatusResponse response = plutusClient.validateSubscription();
		System.out.println(response);
		Assert.assertTrue(response.isSuccess());
	}

}