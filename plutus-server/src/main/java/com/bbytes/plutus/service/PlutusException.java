package com.bbytes.plutus.service;

public class PlutusException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public PlutusException() {
		super();
	}

	public PlutusException(String message) {
		super(message);
	}

	public PlutusException(String message, Throwable cause) {
		super(message, cause);
	}

	public PlutusException(Throwable cause) {
		super(cause);
	}

}
