package com.bbytes.plutus.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(Include.NON_NULL)
public class PlutusRestResponse implements Serializable {

	private static final long serialVersionUID = 5045802370817104910L;

	protected boolean success = false;

	protected String message = null;
	
	protected Object data;

	public PlutusRestResponse() {
		// empty constructor
	}

	public PlutusRestResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}
	
	public PlutusRestResponse(boolean success, Object data) {
		this.data = data;
		this.success = success;
	}

}
