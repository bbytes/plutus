package com.bbytes.plutus.service;

public class ProductStatsException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public ProductStatsException() {
		super();
	}

	public ProductStatsException(String message) {
		super(message);
	}

	public ProductStatsException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductStatsException(Throwable cause) {
		super(cause);
	}

}
