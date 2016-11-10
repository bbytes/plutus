package com.bbytes.plutus.model;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.AppProfile;
import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.mongo.CascadeSave;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class PricingPlan extends BaseEntity {

	// the reference to product object that contain info like product name ,
	// desc etc
	@DBRef
	@CascadeSave
	private Product product;
	
	private String pricingPlanName;

	// usually Map of items that is considered for the product plan like
	// no of users , no of projects , no of candidates allowed to its cost
	// like charge per user 3$ etc
	private Map<String, Number> productPlanItemToCost;

	// the current option of the customer for this subscription , currently only
	// USD and INR supported
	private Currency currency;

	// the app profile it is running in and possible values enterprise, saas,
	// enterpriceSaas
	private AppProfile appProfile;

	// the cycle when invoice or collection of money has to happen . Possible
	// values Monthy, Quarterly, HalfYearly, Annually, Perpetual
	private BillingCycle billingCycle;
}
