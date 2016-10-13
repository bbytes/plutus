package com.bbytes.plutus.client;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.model.SubscriptionInfo;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.response.SubscriptionRegisterRestResponse;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;

public class PlutusClient extends AbstractClient {

	public static PlutusClient create(String baseUrl, String subscriptionKey, String subscriptionSecret,
			AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public static PlutusClient create(String baseUrl, AppProfile appProfile) {
		return new PlutusClient(baseUrl, "", "", appProfile);
	}

	public PlutusClient(String baseUrl, String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		super(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public SubscriptionStatusRestResponse validateSubscription() throws PlutusClientException {
		SubscriptionStatusRestResponse response = get("/subscription/validate", SubscriptionStatusRestResponse.class);
		return response;
	}

	public ProductStatsRestResponse sendStats(ProductPlanStats planStats) throws PlutusClientException {
		// set your entity to send
		ProductStatsRestResponse response = post("/product/stats/create", planStats, ProductStatsRestResponse.class);
		return response;
	}

	public SubscriptionRegisterRestResponse register(SubscriptionInfo subscriptionInfo) throws PlutusClientException {
		// set your entity to send
		SubscriptionRegisterRestResponse response = post("/subscription/register", subscriptionInfo,
				SubscriptionRegisterRestResponse.class);
		return response;
	}
}
