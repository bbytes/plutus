package com.bbytes.plutus.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SubscriptionStatus {

	private boolean valid = false;

	private String message = null;

	private String validTill = null;

	private Double billingAmount = null;

	public SubscriptionStatus(String message, boolean valid, String validTill, double billingAmount) {
		this.message = message;
		this.valid = valid;
		this.validTill = validTill;
		this.billingAmount = billingAmount;
	}

	public SubscriptionStatus(String message, boolean valid) {
		this.message = message;
		this.valid = valid;
	}

}
