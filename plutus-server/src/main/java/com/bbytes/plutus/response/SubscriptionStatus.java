package com.bbytes.plutus.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class SubscriptionStatus {

	private boolean valid = false;

	private String message;

	private String validTill;

	private double amount;

	public SubscriptionStatus(String message, boolean valid, String validTill, double amount) {
		this.message = message;
		this.valid = valid;
		this.validTill = validTill;
		this.amount = amount;
	}

	public SubscriptionStatus(String message, boolean valid) {
		this.message = message;
		this.valid = valid;
	}

}
