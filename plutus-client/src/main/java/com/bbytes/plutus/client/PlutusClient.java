package com.bbytes.plutus.client;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.response.SubscriptionStatusResponse;

public class PlutusClient extends AbstractClient {

	public static PlutusClient createEnterprise(String baseUrl, String subscriptionKey, String subscriptionSecret,
			AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, appProfile);
	}

	public static PlutusClient createSaas(String baseUrl, String subscriptionKey, String subscriptionSecret,
			String tenantId, AppProfile appProfile) {
		return new PlutusClient(baseUrl, subscriptionKey, subscriptionSecret, tenantId, appProfile);
	}

	public PlutusClient(String baseUrl, String subscriptionKey, String subscriptionSecret, AppProfile appProfile) {
		this(baseUrl, subscriptionKey, subscriptionSecret, null, appProfile);
	}

	public PlutusClient(String baseUrl, String subscriptionKey, String subscriptionSecret, String tenantId,
			AppProfile appProfile) {
		super(baseUrl, subscriptionKey, subscriptionSecret, tenantId, appProfile);

	}

	public SubscriptionStatusResponse validateSubscription() throws PlutusClientException {
		SubscriptionStatusResponse response = get("/subscription/validate", SubscriptionStatusResponse.class);
		return response;
	}
}
