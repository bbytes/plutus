package com.bbytes.plutus.response;

import lombok.Data;

@Data
public class PingResponse {

	String status;

	String message;

	String httpStatus;

	public PingResponse(String status, String message, String httpStatus) {
		this.status = status;
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
