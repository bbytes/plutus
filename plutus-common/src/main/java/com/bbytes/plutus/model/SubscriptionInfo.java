package com.bbytes.plutus.model;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;

import lombok.Data;

@Data
public class SubscriptionInfo {

	private String customerName;
	
	private String contactNo;

	private String email;

	private String billingAddress;
	
	private String tenantId;
	
	private Currency currency;
	
	private String productName;
	
	private AppProfile appProfile;
	
	private BillingCycle billingCycle;
	
	
}
