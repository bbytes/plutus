package com.bbytes.plutus.response;

import com.bbytes.plutus.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonInclude(Include.NON_NULL)
public class SubscriptionStatusRestResponse extends PlutusRestResponse {

	private static final long serialVersionUID = 5045802370817104910L;

	private String validTill = null;

	private Double billingAmount = null;

	private Currency currency = null;

	public SubscriptionStatusRestResponse() {
		super();
	}

	public SubscriptionStatusRestResponse(String message, boolean success, String validTill, double billingAmount,
			Currency currency) {
		super(message, success);
		this.validTill = validTill;
		this.billingAmount = billingAmount;
		this.currency = currency;
	}

	public SubscriptionStatusRestResponse(String message, boolean success) {
		super(message, success);
	}

}
