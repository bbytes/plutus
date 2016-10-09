
package com.bbytes.plutus.client;

import org.junit.Assert;
import org.junit.Test;

import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;
import com.bbytes.plutus.util.BillingConstant;

public class PlutusClientTest extends AbstractPlutusClientTest {

	@Test
	public void testValidate() throws PlutusClientException {
		SubscriptionStatusRestResponse response = plutusClient.validateSubscription();
		System.out.println(response);
		Assert.assertTrue(response.isSuccess());
	}

	@Test
	public void testSendStats() throws PlutusClientException {
		ProductPlanStats productPlanStats = new ProductPlanStats();
		productPlanStats.setSubscriptionKey(subscriptionKey);
		productPlanStats.addStats(BillingConstant.STATUSNAP_USER_COUNT, 12);
		ProductStatsRestResponse response = plutusClient.sendStats(productPlanStats);
		System.out.println(response);
		Assert.assertTrue(response.isSuccess());
	}

}