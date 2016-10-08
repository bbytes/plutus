package com.bbytes.plutus.model;

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
	
	private Currency currency;
	
	private DeploymentMode deploymentMode;
	
	private BillingCycle billingCycle;
}
