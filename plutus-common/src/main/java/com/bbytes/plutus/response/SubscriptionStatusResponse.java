package com.bbytes.plutus.response;

import java.io.Serializable;

import com.bbytes.plutus.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class SubscriptionStatusResponse implements Serializable {

	private static final long serialVersionUID = 5045802370817104910L;

	private boolean success = false;

	private String message = null;

	private String validTill = null;

	private Double billingAmount = null;

	private Currency currency = null;

	public SubscriptionStatusResponse() {
		// empty constructor
	}

	public SubscriptionStatusResponse(String message, boolean success, String validTill, double billingAmount,
			Currency currency) {
		this.message = message;
		this.success = success;
		this.validTill = validTill;
		this.billingAmount = billingAmount;
		this.currency = currency;
	}

	public SubscriptionStatusResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

}
