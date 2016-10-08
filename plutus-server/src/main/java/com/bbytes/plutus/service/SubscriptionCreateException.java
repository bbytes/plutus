package com.bbytes.plutus.service;

public class SubscriptionCreateException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public SubscriptionCreateException() {
		super();
	}

	public SubscriptionCreateException(String message) {
		super(message);
	}

	public SubscriptionCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubscriptionCreateException(Throwable cause) {
		super(cause);
	}

}
