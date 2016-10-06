package com.bbytes.plutus.service;

public class LicenseCreateException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public LicenseCreateException() {
		super();
	}

	public LicenseCreateException(String message) {
		super(message);
	}

	public LicenseCreateException(String message, Throwable cause) {
		super(message, cause);
	}

	public LicenseCreateException(Throwable cause) {
		super(cause);
	}

}
