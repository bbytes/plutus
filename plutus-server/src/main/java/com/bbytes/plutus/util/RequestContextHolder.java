package com.bbytes.plutus.util;

import com.bbytes.plutus.enums.AppProfile;

public class RequestContextHolder {

	private static final ThreadLocal<AppProfile> appProfile = new ThreadLocal<AppProfile>();

	private static final ThreadLocal<String> subscriptionKey = new ThreadLocal<String>();

	public static String getSubscriptionKey() {
		return subscriptionKey.get();
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
		appProfile.remove();
		subscriptionKey.remove();
	}

}
