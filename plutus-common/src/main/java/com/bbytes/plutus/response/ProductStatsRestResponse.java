package com.bbytes.plutus.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true)
@JsonInclude(Include.NON_NULL)
public class ProductStatsRestResponse extends PlutusRestResponse {

	private static final long serialVersionUID = 5045802370817104910L;

	public ProductStatsRestResponse() {
		super();
	}

	public ProductStatsRestResponse(String message, boolean success) {
		super(message, success);
	}
	
	public ProductStatsRestResponse(boolean success, Object data) {
		super(success, data);
	}

}
