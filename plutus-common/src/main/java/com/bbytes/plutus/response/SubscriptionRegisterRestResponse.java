package com.bbytes.plutus.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonInclude(Include.NON_NULL)
public class SubscriptionRegisterRestResponse extends PlutusRestResponse {

	private static final long serialVersionUID = 5045802370817104910L;

	private String subscriptionKey;

	private String subscriptionSecret;

	public SubscriptionRegisterRestResponse() {
		super();
	}

	public SubscriptionRegisterRestResponse(String message, boolean success, String subscriptionKey,
			String subscriptionSecret) {
		super(message, success);
		this.subscriptionSecret = subscriptionSecret;
		this.subscriptionKey = subscriptionKey;
	}

	public SubscriptionRegisterRestResponse(String message, boolean success) {
		super(message, success);
	}

}
