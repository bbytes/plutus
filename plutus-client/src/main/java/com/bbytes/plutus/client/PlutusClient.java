package com.bbytes.plutus.client;

import org.springframework.http.HttpEntity;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.model.ProductPlanStats;
import com.bbytes.plutus.response.ProductStatsRestResponse;
import com.bbytes.plutus.response.SubscriptionStatusRestResponse;

public class PlutusClient extends AbstractClient {

	public static PlutusClient create(String baseUrl, String subscriptionKey, String subscriptionSecret,
			AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
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
		HttpEntity<ProductPlanStats> entity = new HttpEntity<ProductPlanStats>(planStats);

		ProductStatsRestResponse response = post("/product/stats/create", entity, ProductStatsRestResponse.class);
		return response;
	}
}
