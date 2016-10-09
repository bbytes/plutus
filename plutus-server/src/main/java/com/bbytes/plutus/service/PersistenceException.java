package com.bbytes.plutus.service;

public class PersistenceException extends RuntimeException {

	private static final long serialVersionUID = 6332569793464266192L;

	public PersistenceException() {
		super();
	}

	public PersistenceException(String message) {
		super(message);
	}

	public PersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PersistenceException(Throwable cause) {
		super(cause);
	}

}
