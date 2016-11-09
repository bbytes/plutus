package com.bbytes.plutus.client;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.model.BillingInfo;
import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.response.SubscriptionRegisterRestResponse;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;
import com.bbytes.plutus.util.URLMapping;

public class PlutusClient extends AbstractClient {

	public static PlutusClient create(String baseUrl, String subscriptionKey, String subscriptionSecret,
			AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public static PlutusClient create(String baseUrl, AppProfile appProfile) {
		return new PlutusClient(baseUrl, "empty", "empty", appProfile);
	}

	public PlutusClient(String baseUrl, String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		super(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public SubscriptionStatusRestResponse validateSubscription() throws PlutusClientException {
		SubscriptionStatusRestResponse response = get(URLMapping.BASE_API_URL + "/subscription/validate",
				SubscriptionStatusRestResponse.class);
		return response;
	}

	public ProductStatsRestResponse sendStats(ProductPlanStats planStats) throws PlutusClientException {
		ProductStatsRestResponse response = post(URLMapping.BASE_API_URL + "/product/stats/create", planStats,
				ProductStatsRestResponse.class);
		return response;
	}
	
	public ProductStatsRestResponse getPricingPlans(String productName) throws PlutusClientException {
		ProductStatsRestResponse response = get(URLMapping.BASE_API_URL + "/pricingPlan/"+productName,
				ProductStatsRestResponse.class);
		return response;
	}
	
	public ProductStatsRestResponse saveBillingInfo(BillingInfo billingInfo) throws PlutusClientException {
		ProductStatsRestResponse response = post(URLMapping.BASE_API_URL + "/subscription/billingInfo", billingInfo,
				ProductStatsRestResponse.class);
		return response;
	}
	
	public ProductStatsRestResponse getPaymentHistory() throws PlutusClientException {
		ProductStatsRestResponse response = get(URLMapping.BASE_API_URL + "/subscription/paymentHistory/"+subscriptionKey,
				ProductStatsRestResponse.class);
		return response;
	}

	public SubscriptionRegisterRestResponse register(SubscriptionInfo subscriptionInfo) throws PlutusClientException {
		// set your entity to send
		SubscriptionRegisterRestResponse response = post(URLMapping.BASE_API_URL + "/subscription/register",
				subscriptionInfo, SubscriptionRegisterRestResponse.class);
		return response;
	}

	public void deleteSubscription() throws PlutusClientException {
		delete(URLMapping.BASE_API_URL + "/subscription");
	}
}
