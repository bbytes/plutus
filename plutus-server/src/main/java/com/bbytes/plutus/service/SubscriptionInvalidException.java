package com.bbytes.plutus.service;

public class SubscriptionInvalidException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public SubscriptionInvalidException() {
		super();
	}

	public SubscriptionInvalidException(String message) {
		super(message);
	}

	public SubscriptionInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubscriptionInvalidException(Throwable cause) {
		super(cause);
	}

}
