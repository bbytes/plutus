package com.bbytes.plutus.util;

import com.bbytes.plutus.enums.AppProfile;

public class RequestContextHolder {

	private static final ThreadLocal<String> tenantIdentifier = new ThreadLocal<String>();

	private static final ThreadLocal<AppProfile> appProfile = new ThreadLocal<AppProfile>();

	private static final ThreadLocal<String> subscriptionKey = new ThreadLocal<String>();

	public static String getTenant() {
		return tenantIdentifier.get();
	}

	public static String getSubscriptionKey() {
		return subscriptionKey.get();
	}

	public static void setTenant(String tenant) {
		tenantIdentifier.set(tenant);
	}

	public static void setSubscriptionKey(String subscriptionKeyValue) {
		subscriptionKey.set(subscriptionKeyValue);
	}

	public static AppProfile getAppProfile() {
		return appProfile.get();
	}

	public static void setAppProfile(AppProfile appProfileValue) {
		appProfile.set(appProfileValue);
	}

	public static void clearContext() {
		tenantIdentifier.remove();
		appProfile.remove();
		subscriptionKey.remove();
	}

}
