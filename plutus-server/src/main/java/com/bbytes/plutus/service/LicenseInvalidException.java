package com.bbytes.plutus.service;

public class LicenseInvalidException extends Exception {

	private static final long serialVersionUID = 6332569793464266192L;

	public LicenseInvalidException() {
		super();
	}

	public LicenseInvalidException(String message) {
		super(message);
	}

	public LicenseInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public LicenseInvalidException(Throwable cause) {
		super(cause);
	}

}
