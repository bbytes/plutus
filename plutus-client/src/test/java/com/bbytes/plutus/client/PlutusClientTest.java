
package com.bbytes.plutus.client;

import org.junit.Assert;
import org.junit.Test;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.ProductName;
import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.response.SubscriptionRegisterRestResponse;
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
		productPlanStats.addStats(BillingConstant.STATUSNAP_PROJECT_COUNT, 5);
		ProductStatsRestResponse response = plutusClient.sendStats(productPlanStats);
		System.out.println(response);
		Assert.assertTrue(response.isSuccess());
	}

	@Test
	public void testRegister() throws PlutusClientException {
		SubscriptionInfo subscriptionInfo = new SubscriptionInfo();
		subscriptionInfo.setAppProfile(AppProfile.saas);
		subscriptionInfo.setBillingAddress("none");
		subscriptionInfo.setBillingCycle(BillingCycle.Monthy);
		subscriptionInfo.setContactNo("1234567899");
		subscriptionInfo.setCurrency(Currency.INR);
		subscriptionInfo.setCustomerName("test");
		subscriptionInfo.setEmail("test@test.com");
		subscriptionInfo.setProductName(ProductName.Statusnap.toString());
		subscriptionInfo.setTenantId("randomTenantid");
		
		PlutusClient plutusClient = PlutusClient.create(BASE_URL, AppProfile.saas);
		SubscriptionRegisterRestResponse response = plutusClient.register(subscriptionInfo);
		System.out.println(response);
		Assert.assertTrue(response.isSuccess());
	}

}