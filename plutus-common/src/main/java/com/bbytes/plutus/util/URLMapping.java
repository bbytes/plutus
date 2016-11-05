package com.bbytes.plutus.util;

public final class URLMapping {
	
	public final static String REST_API_BASE_URL = "api/v1";

	public final static String BASE_API_URL = "/"+REST_API_BASE_URL;
	
	public final static String SUBSCRIPTION_URL = REST_API_BASE_URL+"/subscription";
	
	public final static String PRODUCT_URL = REST_API_BASE_URL+"/product";
	
	public final static String PRICING_PLAN_URL = REST_API_BASE_URL+"/pricingPlan";
	
	public final static String PROD_STATS_URL = PRODUCT_URL+ "/stats";
	
}