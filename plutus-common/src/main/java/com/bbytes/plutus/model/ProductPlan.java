package com.bbytes.plutus.model;

import java.util.Map;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.BillingCycle;
import com.bbytes.plutus.enums.Currency;
import com.bbytes.plutus.enums.DeploymentMode;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class ProductPlan extends BaseEntity {

	@DBRef
	private Product product;
	
	@DBRef
	private Subscription subscription;

	// usually Map of items that is considered for the product plan like
	// no of users , no of projects , no of candidates allowed to its cost
	// like charge per user 3$ etc
	private Map<String, Double> productPlanItemToCost;

	private Currency currency;

	private DeploymentMode deploymentMode;

	private BillingCycle billingCycle;

	private Discount discount;
}
