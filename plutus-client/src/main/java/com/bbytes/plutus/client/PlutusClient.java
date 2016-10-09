package com.bbytes.plutus.client;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.response.SubscriptionStatusResponse;

public class PlutusClient extends AbstractClient {

	public static PlutusClient create(String baseUrl, String subscriptionKey, String subscriptionSecret,
			AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public PlutusClient(String baseUrl, String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		super(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public SubscriptionStatusResponse validateSubscription() throws PlutusClientException {
		SubscriptionStatusResponse response = get("/subscription/validate", SubscriptionStatusResponse.class);
		return response;
	}
}
