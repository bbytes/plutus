package com.bbytes.plutus.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class LicenseStatus {

	private boolean valid = false;

	private String message;

	private String validTill;

	private int count;

	public LicenseStatus(String message, boolean valid, String validTill, int count) {
		this.message = message;
		this.valid = valid;
		this.validTill = validTill;
		this.count = count;
	}

	public LicenseStatus(String message, boolean valid) {
		this.message = message;
		this.valid = valid;
	}

}
