package com.bbytes.plutus.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bbytes.plutus.enums.BillingCycle;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Document
public class ProductPricingPlan extends BaseEntity {

	@DBRef
	private Product product;
	
	private BillingCycle billingCycle;

	private int pricePerUnit;

	private int tax;

}
