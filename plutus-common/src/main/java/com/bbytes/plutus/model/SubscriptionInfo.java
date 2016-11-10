package com.bbytes.plutus.model;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;

import lombok.Data;

/**
 * Used to send the actual values from DB object back to ui , used as DTO This
 * object is not stored in DB
 */
@Data
public class SubscriptionInfo {

	private String customerName;

	private String contactPerson;

	private String contactNo;

	private String email;

	private String billingAddress;

	private String tenantId;

	private String hardwareId;

	private Currency currency;

	private String productName;

	private AppProfile appProfile;

	private BillingCycle billingCycle;

}
